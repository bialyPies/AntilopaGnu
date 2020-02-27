package vsparcheuskaya;

import org.apache.log4j.Logger;
import vsparcheuskaya.entities.Detail;
import vsparcheuskaya.entities.Order;
import vsparcheuskaya.enums.DetailState;
import vsparcheuskaya.enums.OrderStatus;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Manager {
    private static ArrayList<Order> orders;
    private static final Logger logger = Logger.getLogger(Manager.class);

    public static ArrayList<Order> getOrders() {
        return orders;
    }

    Manager (){
        orders = new ArrayList<>();
    }

    public Order createOrder(String name, LocalDate date, LocalDate estimatedDate){
        Order order = new Order(name, date, estimatedDate);   //without details because we create it later
        orders.add(order);
        logger.info("Created order: " + order.toString());
        return order;
    }
    public void editOrder(Order order){
        throw new UnsupportedOperationException();
    }
    public void cancelOrder(int id){    //or by Order
        Order order = orders.stream().filter(o -> id == o.getId()).findFirst().orElse(null);
        if (order != null) {
            order.setOrderStatus(OrderStatus.CANCELED);
            logger.info("Canceled order: " + order.toString());
            //System.out.println("Canceled order: " + order.toString());
        }
    }
    public void giveOrder(){
        throw new UnsupportedOperationException();
    }

    public Detail addDetail(Order order, String name, DetailState state, BigDecimal price) {
        Detail detail = new Detail(name, state, price);
        if (order != null){
            order.getDetails().add(detail);
            logger.info("Added detail: " + detail.toString());
            return detail;
        }
        logger.warn("Order is null");
        return null;
    }
    public Detail addDetail(Order order, Detail detail) {
        if (order != null){
            order.getDetails().add(detail);
            logger.info("Added detail: " + detail.toString());
            return detail;
        }
        logger.warn("Order is null");
        return null;
    }
    public void editDetail() {
        throw new UnsupportedOperationException();
    }
    public void deleteDetail(Order order, Detail detail) throws NoSuchOrderException {
        if (order != null) {
            order.getDetails().remove(detail);
            logger.info("Detail is deleted");
        }else throw new NoSuchOrderException("Order is null");
    }
    public void checkOutDetail() {
        throw new UnsupportedOperationException();
    }

    public ArrayList<Order> findOrderByName(String name){
        Stream<Order> selectedOrders = orders.stream().filter(o -> o.getName().contains(name));
        List<Order> selectedOrdersList = selectedOrders.collect(Collectors.toList());
        if (!selectedOrdersList.isEmpty()) {
            for (Order o : selectedOrdersList){
                //System.out.println(o.toString());
                logger.info("Orders found: " + o.toString());
            }
            return (ArrayList<Order>) selectedOrdersList;
        }
        return null;
    }
    public ArrayList<Order> findOrderByDate(LocalDate from, LocalDate to ){ // add диапазон
        List<Order> selectedOrdersList = orders.stream().filter(o -> o.getDate().equals(from)).collect(Collectors.toList());
        if (!selectedOrdersList.isEmpty()) {
            for (Order o : selectedOrdersList){
                logger.info("Orders found: " + o.toString());
                //System.out.println(o.toString());
            }
            return (ArrayList<Order>) selectedOrdersList;
        }
        return null;
    }
    public ArrayList<Order> findOrderByDetail(Detail detail){

        List<Order> selectedOrdersList = orders.stream()
                .filter(o -> o.getDetails().contains(detail))
                .collect(Collectors.toList());
        if (!selectedOrdersList.isEmpty()) {
            for (Order o : selectedOrdersList){
                //System.out.println(o.toString());
                logger.info("Orders found: " + o.toString());
            }
            return (ArrayList<Order>) selectedOrdersList;
        }
        return null;
    }
    public ArrayList<Order> findOrderByCustomer(){
        throw new UnsupportedOperationException();
    }

    public void serialize(ArrayList<Order> list) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("files/orders.txt"));
        out.writeObject(list);
        out.close();
        logger.info("I have written:");
        logger.info(list.toString());
    }
    public ArrayList<Order> deserialize() throws IOException, ClassNotFoundException {
        ObjectInputStream in =  new ObjectInputStream (new FileInputStream("files/orders.txt"));
        ArrayList<Order> ords = (ArrayList<Order>)in.readObject();
        logger.info("I have read:");
        logger.info(ords.toString());

        logger.info("\nDetails:");
        for (Order o : ords){
            logger.info(o.toString());
            for (Detail d : o.getDetails()){
                logger.info(d.toString());
            }
        }
        return ords;
    }
}
