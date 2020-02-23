package vsparcheuskaya.entities;

import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
public class Photo implements Serializable {    //in process
    private String name;
    private byte[] image; //blob
    private Date date;
    private String note;
}
