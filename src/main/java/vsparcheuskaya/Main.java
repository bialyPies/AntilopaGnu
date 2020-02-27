package vsparcheuskaya;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import vsparcheuskaya.entities.Detail;
import vsparcheuskaya.entities.Order;
import vsparcheuskaya.enums.DetailState;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Main {

    private static Logger logger = LogManager.getLogger(Main.class);
    private final static String baseFile = "files/order.json";

    public static void main(String[] args) {
        Manager manager = new Manager();

        Order order;
        Detail detail, detail2;

        order = manager.createOrder("order1", LocalDate.now(), LocalDate.of(2010, 2, 24));
        manager.addDetail(order, "e1", DetailState.USED, BigDecimal.valueOf(1.1));
        detail = manager.addDetail(order, "e2", DetailState.USED, BigDecimal.valueOf(2.2));
        manager.addDetail(order, "e3", DetailState.USED, BigDecimal.valueOf(3.3));

        manager.createOrder("order2", LocalDate.now(), LocalDate.of(2010, 2, 24));
        order = manager.createOrder("ord", LocalDate.now(), LocalDate.of(2010, 2, 24));
        detail2 = manager.addDetail(order, detail);

        /*System.out.println();
        manager.cancelOrder(1);
        try {
            manager.deleteDetail(null, detail);
        } catch (NoSuchOrderException e) {
            logger.error("it can't be deleted: " + e.getMessage());
        }*/

        /*System.out.println("\n find order by name:");
        manager.findOrderByName("orde");*/

        //manager.findOrderByDetail(detail2);

        /*try {
            manager.serialize(Manager.getOrders());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        try {
            ArrayList<Order> ords = manager.deserialize();
        } catch (IOException | ClassNotFoundException e) {
            logger.error(e.getMessage());
        }*/

        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonorder = mapper.writeValueAsString(order);
            System.out.println("json order " + jsonorder);
            mapper.writeValue(new File(baseFile), order);

            Order newOrder = mapper.readValue(jsonorder, Order.class);
            System.out.println(newOrder);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
