package gov.kui.jmssender.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SenderDto implements Serializable {
    private static final long serialVersionUID = 3088392829089853250L;

    int id;

    @NotBlank(message = "Не указано наименование стороны подписания.")
    String title;
}
