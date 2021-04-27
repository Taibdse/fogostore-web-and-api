package com.example.fogostore.common.constants;

import java.util.ArrayList;
import java.util.List;

public class OrderStatus {
    public final static String NEW = "new";
    public final static String CONTACTING = "contacting";
    public final static String DELIVERING = "delivering";
    public final static String CANCEL = "cancel";
    public final static String DONE = "done";

    public static List<String> getOrderStatuses(){
        List<String> list = new ArrayList<>();
        list.add(NEW);
        list.add(CONTACTING);
        list.add(DELIVERING);
        list.add(CANCEL);
        list.add(DONE);
        return list;
    }
}
