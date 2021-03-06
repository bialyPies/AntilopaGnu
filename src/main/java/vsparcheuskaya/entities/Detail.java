package vsparcheuskaya.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import vsparcheuskaya.enums.DetailState;
import vsparcheuskaya.enums.DetailStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Data
@NoArgsConstructor
public class Detail implements Serializable {
    private int id;
    private int orderId;
    private String name;
    private DetailState state;
    private BigDecimal price;
    private Year releaseYear; //another year
    private String color;
    private String note;
    private List<Photo> photos;
    private List<Photo> photosReceived;
    private DetailStatus status;

    private static int gId = 1;
    { id = gId++; } //temp

    public Detail(String name, DetailState state, BigDecimal price){
        this.name = name;
        this.state = state;
        this.price = price;
    }

    public void addPhoto(String name, byte[] img, LocalDate date, String note){  //add parameter for photo data
        photos.add(new Photo(name, img, date, note));
    }
    public void deletePhoto(){
        throw new UnsupportedOperationException();
    }
    public void editPhoto(String name, String note){
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(Object o) { //многие поля null; что делать с фотографиями?
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Detail detail = (Detail) o;
        return name.equals(detail.name) &&
                state == detail.state &&
                price.equals(detail.price);
    }

    @Override
    public String toString() {
        return name + " " + state + " " + price;
    }
}
