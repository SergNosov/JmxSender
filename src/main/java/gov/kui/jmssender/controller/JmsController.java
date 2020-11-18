package gov.kui.jmssender.controller;

import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.service.DocumentDtoService;
import gov.kui.jmssender.service.JmsSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
public class JmsController {
    private final DocumentDtoService documentDtoService;
    private final JmsSenderService jmsSenderService;

    @Autowired
    public JmsController(DocumentDtoService documentDtoService,
                         JmsSenderService jmsSenderService) {
        this.documentDtoService = documentDtoService;
        this.jmsSenderService = jmsSenderService;
    }

    @GetMapping({"/", "/sender"})
    public String startPage(Model model) {
        jmsSenderService.isJmsAlive();

        model.addAttribute("documentDto", new DocumentDto());
        model.addAttribute("documentDtoList", documentDtoService.getAllDtos());

        return "sender";
    }

    @PostMapping("/sender")
    public String submitDocument(@Valid DocumentDto documentDto, BindingResult bindingResult, Model model) {

        if (!bindingResult.hasErrors()) {

            boolean messageIsSending = jmsSenderService.sendMessage(documentDto).isEmpty();

            if (messageIsSending) {
                model.addAttribute("status", "Внимание! Документ с такими реквизитами отправлен ранее.");
            }
        }

        model.addAttribute("documentDtoList", documentDtoService.getAllDtos());
        return "sender";
    }
}
