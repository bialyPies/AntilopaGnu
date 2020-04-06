package vsparcheuskaya.database;

import org.apache.log4j.Logger;
import vsparcheuskaya.entities.Detail;
import vsparcheuskaya.entities.Order;
import vsparcheuskaya.enums.DetailState;
import vsparcheuskaya.enums.DetailStatus;
import vsparcheuskaya.enums.OrderStatus;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    private static final Logger logger = Logger.getLogger(Controller.class);
    private Connection connection;
    private final static String SQL_GET_ORDERS = "select * from orders";
    private final static String SQL_GET_DETAILS = "select * from detail";
    private final static String SQL_GET_DETAILS_FROM_ORDER = "select * from detail where orderId =?";
    private final static String SQL_GET_ORDER_BY_ID = "select * from orders where orderid=? ";
    private final static String SQL_GET_ORDER_BY_DATE = "select * from orders where orderDate=? ";
    private final static String SQL_GET_ORDER_BY_DATE_PERIOD = "SELECT * FROM orders WHERE DATE(orderDate) BETWEEN ? AND ?;";
    private final static String SQL_GET_ORDER_BY_NAME = "SELECT * FROM orders WHERE orderName like ?";
    private final static String SQL_GET_ORDER_BY_DETAIL = "select * from orders where orderId in (select orderId from detail where detailName like ? );";
    private final static String SQL_GET_ORDER_BY_CUSTOMER = "select * from orders where orderCustomerName like ?;";
    //insert
    private final static String SQL_INSERT_ORDER_REQUIRED = "insert into orders(orderName, orderDate, orderEstimatedDate) values (?, ?, ?);";
    private final static String SQL_INSERT_DETAIL_REQUIRED = "insert into detail(orderId, detailName, detailPrice, detailState) values (?, ?, ?, ?);";
    private final static String SQL_DELETE_ORDER_BY_ID = "delete from orders where orderId = ?;";
    private final static String SQL_DELETE_DETAIL_BY_ID = "delete from detail where detailId = ?;";


    public Controller() throws SQLException {
        this.connection = ConnectorDB.getConnection(); //static method for establishing a database connection
        logger.info("Connection received");
    }
    public void closeConnection() throws SQLException {
        connection.close();
        logger.info("Connection is closed");
    }

    public ArrayList<Order> getOrders(){
        ArrayList<Order> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_GET_ORDERS);){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(setOrderInfo(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return list;
    }
    public ArrayList<Detail> getDetails(){
        ArrayList<Detail> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(SQL_GET_DETAILS);){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(setDetailInfo(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return list;
    }


    //searching func
    public Order getOrderById(final int id) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(SQL_GET_ORDER_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return setOrderInfo(rs);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return null;
    }
    public ArrayList<Order> getOrderByDate(LocalDate date){
        PreparedStatement ps = null;
        ArrayList<Order> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(SQL_GET_ORDER_BY_DATE);
            ps.setString(1, String.valueOf(date));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(setOrderInfo(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return list;
    }
    public ArrayList<Order> getOrderByDatePeriod(LocalDate from, LocalDate to){
        PreparedStatement ps;
        ArrayList<Order> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(SQL_GET_ORDER_BY_DATE_PERIOD);
            ps.setString(1, String.valueOf(from));
            ps.setString(2, String.valueOf(to));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(setOrderInfo(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return list;
    }
    public ArrayList<Order> getOrderByName(String name){
        PreparedStatement ps;
        ArrayList<Order> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(SQL_GET_ORDER_BY_NAME);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(setOrderInfo(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return list;
    }
    public ArrayList<Order> getOrderByDetail(String name){
        PreparedStatement ps;
        ArrayList<Order> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(SQL_GET_ORDER_BY_DETAIL);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(setOrderInfo(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return list;
    }
    public ArrayList<Order> getOrderByCustomer(String name){
        PreparedStatement ps;
        ArrayList<Order> list = new ArrayList<>();
        try {
            ps = connection.prepareStatement(SQL_GET_ORDER_BY_CUSTOMER);
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(setOrderInfo(rs));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return list;
    }

    //DML IUD
    public void insertOrder(String name, LocalDate date, LocalDate eDate){
        try {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT_ORDER_REQUIRED);
            ps.setString(1, name);
            ps.setString(2, String.valueOf(date));
            ps.setString(3, String.valueOf(eDate));
            int row = ps.executeUpdate();

            if (row == 1) logger.info("row " + name + " is added");
            else logger.warn("smth is wrong with row adding");

        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
    public void insertDetail(int orderId, String name, BigDecimal price, DetailState isused){
        try(PreparedStatement ps = connection.prepareStatement(SQL_INSERT_DETAIL_REQUIRED)){
            ps.setInt(1, orderId);
            ps.setString(2, name);
            ps.setBigDecimal(3, price);
            ps.setString(4, String.valueOf(isused));
            int row = ps.executeUpdate();

            if (row == 1) logger.info("row " + name + " is added");
            else logger.warn("smth is wrong with row adding");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
    public void updateOrder(){
        throw new UnsupportedOperationException();
    }
    public void updateDetail(){
        throw new UnsupportedOperationException();
    }
    public void deleteOrder(int id){
        try(PreparedStatement ps = connection.prepareStatement(SQL_DELETE_ORDER_BY_ID)){
            ps.setInt(1, id);
            int row = ps.executeUpdate();

            if (row == 1) logger.info("row " + id + " is added");
            else logger.warn("smth is wrong with row deleting");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
    public void deleteDetail(int id){
        try(PreparedStatement ps = connection.prepareStatement(SQL_DELETE_DETAIL_BY_ID)){
            ps.setInt(1, id);
            int row = ps.executeUpdate();

            if (row == 1) logger.info("row " + id + " is added");
            else logger.warn("smth is wrong with row deleting");
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    //create Order from ResulSet
    private Order setOrderInfo(ResultSet rs) throws SQLException {
        Order rOrder = new Order();
        rOrder.setId(rs.getInt("orderId"));
        rOrder.setName(rs.getString("orderName"));
        rOrder.setCustomerName(rs.getString("orderCustomerName"));

        //logger.info(rs.getString("orderStatus"));
        rOrder.setOrderStatus(OrderStatus.valueOf(rs.getString("orderStatus")));
//        rOrder.setOrderStatus(OrderStatus.values()[rs.getInt("orderStatus")]);

        rOrder.setTotalCost(rs.getBigDecimal("orderTotalCost"));
        rOrder.setDate(rs.getDate("orderDate").toLocalDate());
        rOrder.setEstimatedDate(rs.getDate("orderEstimatedDate").toLocalDate());
        //rOrder.setCompletionDate(rs.getDate("orderCompletionDate").toLocalDate());    //it's null //optional
        return rOrder;
    }
    private Detail setDetailInfo(ResultSet rs) throws SQLException {
        Detail rDetail = new Detail();
        rDetail.setId(rs.getInt("detailId"));
        rDetail.setOrderId(rs.getInt("orderId"));
        rDetail.setName(rs.getString("detailName"));
        rDetail.setState(DetailState.valueOf(rs.getString("detailState")));
        rDetail.setStatus(DetailStatus.valueOf(rs.getString("detailStatus")));
        rDetail.setPrice(rs.getBigDecimal("detailPrice"));
        //rDetail.setReleaseYear();
        rDetail.setColor(rs.getString("detailColor"));
        rDetail.setNote(rs.getString("detailNote"));

        return rDetail;
    }

    public static void main(String[] args) {
        try {
            ArrayList<Order> list = new ArrayList<>();
            Controller manager = new Controller();
            Order findOrder;

            //findOrder = manager.getOrderById(16);
            //logger.info(findOrder);

//            list = manager.getOrderByDate(LocalDate.parse("2020-02-28"));
//            logger.info(list);
//            list.clear();

//            list = manager.getOrderByDatePeriod(LocalDate.parse("2020-01-28"), LocalDate.parse("2020-03-28"));
//            logger.info(list);

//            list = manager.getOrderByName("e");
//            logger.info(list);

//            list = manager.getOrderByDetail("e");
//            logger.info(list);

            //insert
            //manager.insertOrder("e1", LocalDate.parse("2020-01-28"), LocalDate.parse("2020-03-28") );
            //manager.insertDetail(7, "eng4", BigDecimal.valueOf(5.89), DetailState.USED);

            List<Order> orderList = manager.getOrders();
            for(Order o : orderList){
                System.out.println(o.getDate());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
