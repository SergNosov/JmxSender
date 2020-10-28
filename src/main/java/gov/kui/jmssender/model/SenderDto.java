package gov.kui.jmssender.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SenderDto {
    int id;

    @NotBlank(message = "Не указано наименование стороны подписания.")
    String title;
}
