package gov.kui.jmssender.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class DocumentDto implements Serializable {
    private static final long serialVersionUID = -5505354011800084672L;

    private int id;

    @NotBlank(message = "Не указан номер документа.")
    private String number;

    @NotNull(message = "Не указана дата документа.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate docDate;

    @NotBlank(message = "Не указан заголовок документа.")
    private String title;

    private String content = "";

    @Valid
    @NotNull(message = "Не указан тип документа.")
    private DoctypeDto doctype;

    @Valid
    @NotEmpty(message = "Не указана сторона подписания.")
    private List<SenderDto> senders =  new ArrayList<>();

    @ToString.Exclude
    private transient byte[] byteOfUploadFile;
}
