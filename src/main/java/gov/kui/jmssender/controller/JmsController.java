package gov.kui.jmssender.controller;

import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.model.FormData;
import gov.kui.jmssender.service.JmsSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.io.IOException;
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
        jmsSenderService.isJmsAlive();

        model.addAttribute("formData", new FormData(new DocumentDto()));
        model.addAttribute("documentDtoList", jmsSenderService.getAllDtos());

        return "sender";
    }

    @PostMapping("/sender")
    public String submitDocument(@Valid FormData formData, BindingResult bindingResult, Model model) {

        if (!bindingResult.hasErrors()) {
            Optional<DocumentDto> resultOfSending = jmsSenderService.sendMessage(formData.getDocumentDto());

            if (resultOfSending.isEmpty()) {
                model.addAttribute("status", "Внимание! Документ с такими реквизитами отправлен ранее.");
            }
        } else {
            log.info("--- binding errors: "+ bindingResult.getAllErrors());
        }

        model.addAttribute("documentDtoList", jmsSenderService.getAllDtos());
        return "sender";
    }
}
