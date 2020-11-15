package gov.kui.jmssender.controller;

import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.service.DocumentDtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.validation.Valid;

@Controller
@Slf4j
public class JmsController {
    private final DocumentDtoService documentDtoService;
    private final CachingConnectionFactory cachingConnectionFactory;

    @Autowired
    public JmsController(DocumentDtoService documentDtoService, CachingConnectionFactory cachingConnectionFactory) {
        this.documentDtoService = documentDtoService;
        this.cachingConnectionFactory = cachingConnectionFactory;
    }

    @GetMapping({"/", "/sender"})
    public String startPage(Model model) {
        this.checkJMSConnection();

        model.addAttribute("documentDto", new DocumentDto());
        model.addAttribute("documentDtoList", documentDtoService.getAllDtos());

        return "sender";
    }

    @PostMapping("/sender")
    public String submitDocument(@Valid DocumentDto documentDto, BindingResult bindingResult, Model model) {

        if (!bindingResult.hasErrors()) {
            if (documentDtoService.sendDto(documentDto).isEmpty()) {
                model.addAttribute("status", "Внимание! Документ с такими реквизитами отправлен ранее.");
            }
        }

        model.addAttribute("documentDtoList", documentDtoService.getAllDtos());
        return "sender";
    }

    private void checkJMSConnection(){
        try (Connection con = cachingConnectionFactory.createConnection()){
            log.info("--- con: "+con.getMetaData().getJMSProviderName());
        } catch (JMSException jmsEx) {
            log.error("--- jmsEx: "+jmsEx);
            throw new RuntimeException(jmsEx);
        }
    }
}
