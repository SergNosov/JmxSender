package gov.kui.jmssender.controller;

import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.service.DocumentDtoService;
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

    @Autowired
    public JmsController(DocumentDtoService documentDtoService) {
        this.documentDtoService = documentDtoService;
    }

    @GetMapping({"/", "/sender"})
    public String startPage(Model model) {
        model.addAttribute("documentDto", new DocumentDto());
        model.addAttribute("documentDtoList", documentDtoService.getAllDtos());
        return "sender";
    }

    @PostMapping("/sender")
    public String submitDocument(@Valid DocumentDto documentDto, BindingResult bindingResult, Model model) {

        if (!bindingResult.hasErrors()) {
            documentDtoService.sendDto(documentDto);
        }
        model.addAttribute("documentDtoList", documentDtoService.getAllDtos());

        model.addAttribute("status", "double sending");

        return "sender";
    }
}
