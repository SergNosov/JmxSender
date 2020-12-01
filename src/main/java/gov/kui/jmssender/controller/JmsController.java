package gov.kui.jmssender.controller;

import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.model.SenderDto;
import gov.kui.jmssender.service.JmsSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.print.Doc;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

        DocumentDto documentDto = new DocumentDto();
        SenderDto senderDto = new SenderDto();
        senderDto.setTitle("first");

        documentDto.getSenders().add(senderDto);

        model.addAttribute("documentDto", documentDto);
        model.addAttribute("documentDtoList", jmsSenderService.getAllDtos());

        return "sender";
    }

    @PostMapping("/sender")
    public String submitDocument(@Valid DocumentDto documentDto, BindingResult bindingResult, Model model) {

        if (!bindingResult.hasErrors()) {

            boolean alreadySent = jmsSenderService.sendMessage(documentDto).isEmpty();

            if (alreadySent) {
                model.addAttribute("status", "Внимание! Документ с такими реквизитами отправлен ранее.");
            }
        }

        model.addAttribute("documentDtoList", jmsSenderService.getAllDtos());
        return "sender";
    }
}
