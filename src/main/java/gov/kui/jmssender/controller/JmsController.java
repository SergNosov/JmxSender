package gov.kui.jmssender.controller;

import gov.kui.jmssender.dao.DocumentDtoRepository;
import gov.kui.jmssender.model.DocumentDto;
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

    private final DocumentDtoRepository documentRepository;

    @Autowired
    public JmsController(DocumentDtoRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @GetMapping({"/", "/sender"})
    public String startPage(Model model) {
        model.addAttribute("documentDto", new DocumentDto());
        model.addAttribute("documentDtoList", documentRepository.getAllDtos());
        return "sender";
    }

    @PostMapping("/sender")
    public String submitDocument(@Valid DocumentDto documentDto, BindingResult bindingResult, Model model) {

        if (!bindingResult.hasErrors()) {
            documentRepository.addDto(documentDto);
        }
        model.addAttribute("documentDtoList", documentRepository.getAllDtos());

        return "sender";
    }
}
