package gov.kui.jmssender.model;

import lombok.Data;

@Data
public class DocumentPrototype {
    private String number;
    private String docDate;
    private String title;
    private String content="";
    private String doctype;
    private String Sender;
}
