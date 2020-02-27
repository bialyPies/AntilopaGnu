package vsparcheuskaya.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class Email {
    private ArrayList<String> emails;
    private String subject;
    private String template;
    private String text;

    public void send(){
        throw new UnsupportedOperationException();
    }
    public void cancel(){
        throw new UnsupportedOperationException();
    }
}
