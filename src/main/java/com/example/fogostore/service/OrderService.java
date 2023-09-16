package com.example.fogostore.service;

import com.example.fogostore.common.constants.OrderSortBy;
import com.example.fogostore.common.constants.OrderStatus;
import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.dto.order.OrderDetailDto;
import com.example.fogostore.dto.order.OrderDto;
import com.example.fogostore.dto.product.ProductDto;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.model.*;
import com.example.fogostore.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface OrderService {
    ResultBuilder submitOrder(OrderDto orderDto);
    OrderDto getById(Integer id);
    ResultBuilder delete(Integer id);
    ResultBuilder update(OrderDto orderDto);
    Page<OrderDto> searchByAdmin(String customer, Date from, Date to, String status, int page, int size, String sortBy);
}

@Service
class OrderServiceImpl implements OrderService{

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductService productService;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    ProductTypeRepository productTypeRepository;

    @Autowired
    ModelMapper modelMapper;

    private final String ORDER_NOTFOUND = "Không tìm thấy order này";
    private final String ORDER_DELETED = "Order này đã bị xóa!";
    private final String FULLNAME_REQUIRED = "Họ tên không được để trống!";
    private final String INVALID_EMAIL = "Email không hợp lệ!";
    private final String INVALID_PHONE = "SĐT không hợp lệ!";
    private final String ADDRESS_REQUIRED = "Địa chỉ không để trống!";

    private HashMap<String, String> validateOrder(OrderDto orderDto){
        HashMap<String, String> errors = new HashMap<>();
        if(StringUtils.isEmpty(orderDto.getCustomerFullname())){
            errors.put("FULLNAME_REQUIRED", FULLNAME_REQUIRED);
        }

        if(!CustomStringUtils.isValidEmail(orderDto.getCustomerEmail())){
            errors.put("INVALID_EMAIL", INVALID_EMAIL);
        }

        if(!CustomStringUtils.isValidPhone(orderDto.getCustomerPhone())){
            errors.put("INVALID_PHONE", INVALID_PHONE);
        }

        if(StringUtils.isEmpty(orderDto.getCustomerAddress())){
            errors.put("ADDRESS_REQUIRED", ADDRESS_REQUIRED);
        }

        return errors;
    }

    @Override
    public ResultBuilder submitOrder(OrderDto orderDto) {
        ResultBuilder result = ResultBuilder.build();

        //validate order
        HashMap<String, String> errors = validateOrder(orderDto);
        if(errors.size() > 0) return result.success(false).errors(errors);

        //save order
        Order order = modelMapper.map(orderDto, Order.class);
        order.setStatus(OrderStatus.NEW);
        order.setActive(true);
        order = orderRepository.save(order);

        //save order details
        List<OrderDetail> savedOrderDetails = new ArrayList<>();
        for (OrderDetailDto orderDetailDto : orderDto.getOrderDetails()) {
            OrderDetail orderDetail = modelMapper.map(orderDetailDto, OrderDetail.class);
            Product product = productRepository.findById(orderDetail.getProductId()).orElse(null);
            if(product != null && product.isActive() && product.isAvailable()){
                ProductDto productDto = productService.toDto(product, null);
                orderDetail.setProductImage(productDto.getMainImage());
                orderDetail.setProductPrice(productDto.getPrice());
                orderDetail.setProductName(product.getName());
                orderDetail.setOrderId(order.getId());
                if(orderDetail.getProductTypeId() != null){
                    ProductType productType = productTypeRepository.findById(orderDetail.getProductTypeId()).orElse(null);
                    if(productType != null){
                        orderDetail.setProductTypeName(productType.getName());
                        orderDetail.setProductPrice(productType.getPrice());
                    } else {
                        orderDetail.setProductTypeId(null);
                    }
                }
                savedOrderDetails.add(orderDetail);
            }
        }
        orderDetailRepository.saveAll(savedOrderDetails);

        return result.success(true).data(order);
    }

    @Override
    public ResultBuilder update(OrderDto orderDto) {
        ResultBuilder result = ResultBuilder.build();
        Order order = orderRepository.findById(orderDto.getId()).orElse(null);
        if(order == null){
            return result.success(false).errors("ORDER_NOTFOUND", ORDER_NOTFOUND);
        } else if(!order.isActive()) {
            return result.success(false).errors("ORDER_DELETED", ORDER_DELETED);
        } else {
            Order updatedOrder = modelMapper.map(orderDto, Order.class);
            updatedOrder.setCreatedAt(order.getCreatedAt());
            orderRepository.save(updatedOrder);
        }
        return result.success(true).data(order);
    }

    @Override
    public OrderDto getById(Integer id) {
        if(id == null) return null;
        Order order = orderRepository.findById(id).orElse(null);
        return order != null ? toDto(order) : null;
    }

    @Override
    public ResultBuilder delete(Integer id) {
        ResultBuilder result = ResultBuilder.build();
        Order order = orderRepository.findById(id).orElse(null);
        if(order == null || !order.isActive()){
            return result.success(false).errors("ORDER_NOTFOUND", ORDER_NOTFOUND);
        } else {
            order.setActive(false);
            orderRepository.save(order);
        }
        return result.success(true).data(order);
    }

    @Override
    public Page<OrderDto> searchByAdmin(String customer, Date from, Date to, String status, int page, int size, String sortBy) {
        Sort sort = Sort.by("id").descending();
        switch (sortBy){
            case OrderSortBy.CREATED_AT_ASC: sort = Sort.by("id").ascending(); break;
            case OrderSortBy.CREATED_AT_DESC: sort = Sort.by("id").descending(); break;
        }
        Page<Order> orderPage;
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        if("all".equals(status)){
            orderPage = orderRepository.findByAdminWithoutStatus(customer, from, to, pageable);
        } else {
            orderPage = orderRepository.findByAdmin(customer, from, to, status, pageable);
        }

        Page<OrderDto> orderDtoPage = orderPage.map(new Function<Order, OrderDto>() {
            @Override
            public OrderDto apply(Order order) {
                return toDto(order);
            }
        });

        return orderDtoPage;
    }

    public OrderDto toDto(Order order){
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);

        final double[] totalProductPrice = {0};
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
        List<OrderDetailDto> orderDetailDtoList = orderDetails.stream().map(orderDetail -> {
            OrderDetailDto o = modelMapper.map(orderDetail, OrderDetailDto.class);
            double subTotal = o.getProductPrice() * o.getQuantity();
            o.setTotalPrice(subTotal);
            totalProductPrice[0] = totalProductPrice[0] + subTotal;
            return o;
        }).collect(Collectors.toList());
        orderDto.setOrderDetails(orderDetailDtoList);
        orderDto.setTotalProductPrice(totalProductPrice[0]);
        orderDto.setTotalPrice(totalProductPrice[0] + orderDto.getShippingMoney());

        // PaymentMethod paymentMethod = paymentMethodRepository.findById(order.getPaymentMethodId()).orElse(null);
        // orderDto.setPaymentMethod(paymentMethod);

        return orderDto;
    }
}
