package gov.kui.jmssender.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class DocumentDto {
    private int id;

    @NotBlank(message = "Необходимо указать заголовок документа")
    private String number;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate docDate;

    private String title;
    private String content = "";
    private DoctypeDto doctype;
    private SenderDto sender;
}
