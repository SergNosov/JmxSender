package gov.kui.jmssender.controller;

import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.model.FileEntity;
import gov.kui.jmssender.model.FormData;
import gov.kui.jmssender.service.JmsProducerService;
import gov.kui.jmssender.service.JmsSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@Slf4j
public class JmsController {
    private final JmsSenderService jmsSenderService;

    @Autowired
    public JmsController(JmsSenderService jmsSenderService) {
        this.jmsSenderService = jmsSenderService;
    }

    @GetMapping({"/", "/sender"})
    public String startPage(Model model) {
        jmsSenderService.isAvailable();

        model.addAttribute("formData", new FormData(new DocumentDto()));
        model.addAttribute("documentDtoList", jmsSenderService.getAllDtos());

        return "sender";
    }

    @PostMapping("/sender")
    public String submitDocument(@Valid FormData formData,
                                 BindingResult bindingResult,
                                 Model model) {
        jmsSenderService.isAvailable();

        if (!bindingResult.hasErrors()) {

            Optional<DocumentDto> resultOfSending = jmsSenderService.sendMessage(
                    this.getDocumentDtoFromFormData(formData)
            );

            if (resultOfSending.isEmpty()) {
                model.addAttribute("status", "Внимание! Документ с такими реквизитами отправлен ранее.");
            }
        } else {
            log.info("--- binding errors: " + bindingResult.getAllErrors());
        }

        model.addAttribute("documentDtoList", jmsSenderService.getAllDtos());
        return "sender";
    }

    private DocumentDto getDocumentDtoFromFormData(FormData formData) {
        DocumentDto documentDto = formData.getDocumentDto();
        MultipartFile multipartFile = formData.getUploadFile();

        if (multipartFile != null && !multipartFile.isEmpty()) {
            documentDto.setFileEntity(FileEntity.of(multipartFile));
        }
        return documentDto;
    }
}
