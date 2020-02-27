package vsparcheuskaya.entities;

import lombok.Data;
import vsparcheuskaya.enums.DetailState;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class Customer {
    private CustomerInfo info;
    private ArrayList<Order> customerOrders;

    @Data
    public static class CustomerInfo{
        private String name;
    }

    public void createOrder(String name, LocalDate date){
        Order order = new Order(name, date );   //without details because we create it later
        customerOrders.add(order);
        System.out.println("Created customer order: " + order.toString());
    }

    public void addDetail(Order order, String name, DetailState state, BigDecimal price) {
        Detail detail = new Detail(name, state, price);
        order.getDetails().add(detail);
    }
}
