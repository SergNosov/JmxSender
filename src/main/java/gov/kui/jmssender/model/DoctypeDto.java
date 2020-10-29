package gov.kui.jmssender.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class DoctypeDto implements Serializable {
    private static final long serialVersionUID = 3618473513965230114L;

    int id;

    @NotBlank(message = "Не указано наименование документа.")
    String title;
}
