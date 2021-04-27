<%@ tag import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="title" type="java.lang.String" %>
<%@ attribute name="brandMap" type="java.util.Map<java.lang.Integer, com.example.fogostore.common.utils.tree.Node>" %>
<%@ attribute name="onMenu" type="java.lang.Boolean" %>

<%
    String random = new Date().getTime() + "";
%>

<c:forEach items="${brandMap}" var="brand">
    <li>
        <a class="d-inline-block w-85"
           href="/hang-xe/${brand.value.value.slug}">${brand.value.value.name}</a>
        <c:if test="${brand.value.children != null && brand.value.children.size() > 0}">
            <c:if test="${!onMenu}">
                <span class="float-right" data-toggle="collapse"
                      data-target="#brand${brand.key}_<%=random%>"
                      style="font-size: 15px; cursor: pointer">
                    <i class="fas fa-plus"></i>
                </span>
            </c:if>

            <ul class="collapse" id="brand${brand.key}_<%=random%>" style="padding-left: 10px">
                <c:forEach items="${brand.value.children}" var="child">
                    <li>
                        <a class="d-inline-block w-85"
                           href="/hang-xe/${child.value.slug}">${child.value.name}</a>
                    </li>
                    <c:if test="${child.children != null && child.children.size() > 0}">
                        <c:if test="${!onMenu}">
                            <span class="float-right" data-toggle="collapse"
                                  data-target="#brand${brand.key}_<%=random%>"
                                  style="font-size: 15px; cursor: pointer">
                                <i class="fas fa-plus"></i>
                            </span>
                        </c:if>

                        <ul class="collapse" id="brand${brand.key}_<%=random%>" style="padding-left: 20px">
                            <c:forEach items="${child.children}" var="grandchild">
                                <a class="d-inline-block"
                                   href="/hang-xe/${grandchild.value.slug}">${grandchild.value.name}</a>
                            </c:forEach>
                        </ul>
                    </c:if>
                </c:forEach>
            </ul>
        </c:if>
    </li>
</c:forEach>

