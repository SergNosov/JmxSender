package gov.kui.jmssender.service.impl;

import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.service.DocumentDtoService;
import gov.kui.jmssender.service.JmsProducerService;
import gov.kui.jmssender.service.JmsSenderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JmsSenderServiceImpl implements JmsSenderService {

    private final DocumentDtoService documentDtoService;
    private final JmsProducerService jmsProducerService;

    @Autowired
    public JmsSenderServiceImpl(DocumentDtoService documentDtoService, JmsProducerService jmsProducerService) {
        this.documentDtoService = documentDtoService;
        this.jmsProducerService = jmsProducerService;
    }

    @Override
    public Optional<DocumentDto> sendMessage(DocumentDto documentDto) {

        if (!documentDtoService.isExists(documentDto)){
            jmsProducerService.send(documentDto);
            return documentDtoService.save(documentDto);//todo если exception?
        }

        return Optional.empty();
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        return documentDtoService.getAllDtos();
    }

    @Override
    public void isJmsAlive() {
        jmsProducerService.isJmsAlive();
    }
}
