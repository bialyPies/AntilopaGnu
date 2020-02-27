package vsparcheuskaya.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.NoArgsConstructor;
import vsparcheuskaya.enums.OrderStatus;
import vsparcheuskaya.util.LocalDateDeserializer;
import vsparcheuskaya.util.LocalDateSerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@NoArgsConstructor
public class Order implements Serializable {

    private int id;
    private String name;    //
    private String customerName;
    private OrderStatus orderStatus;
    private BigDecimal totalCost;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date; //
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate estimatedDate;    //
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
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
