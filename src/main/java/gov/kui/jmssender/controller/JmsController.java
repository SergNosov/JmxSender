package gov.kui.jmssender.controller;

import gov.kui.jmssender.model.DocumentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class JmsController {

    private List<DocumentDto> documentDtoList = new ArrayList<>();

    @GetMapping({"/","/showForm"})
    public String startPage(Model model){
        model.addAttribute("documentDto", new DocumentDto());
        model.addAttribute("documentDtoList", documentDtoList);
        return "sender";
    }

    @PostMapping("/showForm")
    public String  submitDocument(@Valid DocumentDto documentDto, BindingResult bindingResult, Model model){

        if (!bindingResult.hasErrors()) {
            documentDtoList.add(documentDto);
        }
        model.addAttribute("documentDtoList", documentDtoList);

        return "sender";
    }
}
