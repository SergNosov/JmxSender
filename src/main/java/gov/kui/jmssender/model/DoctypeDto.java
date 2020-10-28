package gov.kui.jmssender.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DoctypeDto {
    int id;

    @NotBlank(message = "Не указано наименование документа.")
    String title;
}
