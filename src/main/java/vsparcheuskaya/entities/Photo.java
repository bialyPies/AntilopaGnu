package vsparcheuskaya.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vsparcheuskaya.util.LocalDateDeserializer;
import vsparcheuskaya.util.LocalDateSerializer;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Photo implements Serializable {    //in process
    private String name;
    private byte[] image; //blob
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;
    private String note;
}
