package vsparcheuskaya.entities;

import java.util.ArrayList;

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
