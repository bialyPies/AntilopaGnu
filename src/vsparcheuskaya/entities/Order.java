package vsparcheuskaya.entities;

import lombok.Data;
import vsparcheuskaya.enums.OrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
public class Order implements Serializable {

    private int id;
    private String name;    //
    private String customerName;
    private OrderStatus orderStatus;
    private BigDecimal totalCost;
    private LocalDate date; //
    private LocalDate estimatedDate;    //
    private LocalDate completionDate;
    private int numberOfDetails;

    private ArrayList<Detail> details;

    private static int gId = 1;
    { id = gId++; } //temp

    public Order(String name, LocalDate date){
        this.name = name;
        this.date = date;
        this.details = new ArrayList<>();
    }
    public Order(String name, LocalDate date, LocalDate estimatedDate){
        this(name, date);
        this.estimatedDate = estimatedDate;
    }

    @Override
    public String toString(){
        return id + " " + name + " " + orderStatus;
    }
}
