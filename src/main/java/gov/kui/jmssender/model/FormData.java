package gov.kui.jmssender.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class FormData {

    @Valid
    @NotNull
    private DocumentDto documentDto;

    private MultipartFile uploadFile;

    public FormData(@Valid @NotNull DocumentDto documentDto) {
        this.documentDto = documentDto;
    }

    @Override
    public String toString() {
        if (uploadFile != null) {
            return "FormData{" +
                    "documentDto = " + documentDto +
                    ", uploadFile.size = " + uploadFile.getSize() +
                    ", uploadFile.name = " + uploadFile.getName() +
                    '}';
        }
        return "FormData{ documentDto = " + documentDto+" }";
    }
}
