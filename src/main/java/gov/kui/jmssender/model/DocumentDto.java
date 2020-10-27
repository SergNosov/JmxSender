package gov.kui.jmssender.model;



import gov.kui.jmssender.model.dto.DoctypeDto;
import gov.kui.jmssender.model.dto.SenderDto;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class DocumentDto {
    private int id;
    private String number;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate docDate;

    private String title;
    private String content="";
    private DoctypeDto doctype;
    private SenderDto sender;
}
