package gov.kui.jmssender.service.impl;

import gov.kui.jmssender.dao.DocumentDtoRepository;
import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.service.DocumentDtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DocumentDtoServiceImpl implements DocumentDtoService {
    private final DocumentDtoRepository documentDtoRepository;

    @Autowired
    public DocumentDtoServiceImpl(DocumentDtoRepository documentDtoRepository ) {
        this.documentDtoRepository = documentDtoRepository;
    }

    @Override
    public Optional<DocumentDto> save(final DocumentDto documentDto) {
        this.checkDocumentDto(documentDto);

        if (!this.isExists(documentDto)) {
            documentDtoRepository.save(documentDto);
            return Optional.of(documentDto);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean isExists(DocumentDto documentDto) {
        Assert.notNull(documentDto, "documentDto не может быть null");
        return documentDtoRepository.isExists(documentDto);
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        return documentDtoRepository.getAllDtos();
    }

    private void checkDocumentDto(final DocumentDto documentDto) {
        log.info("--- documentDto:"+documentDto);
        log.info("--- documentDto.getSenders().isEmpty: "+documentDto.getSenders().isEmpty());
        log.info("--- documentDto.getSenders(): "+documentDto.getSenders().size());
        log.info("--- documentDto.getSenders(): "+documentDto.getSenders());

        Assert.notNull(documentDto, "документ не может быть null");
        Assert.notNull(documentDto.getDoctype(), "Не указан тип документа.");
        Assert.hasText(documentDto.getDoctype().getTitle(), "Не указан заголовок документа.");
        Assert.hasText(documentDto.getNumber(), "Не указан номер документа.");
        Assert.notNull(documentDto.getDocDate(), "Не указана дата документа (null)");
        Assert.hasText(documentDto.getDocDate().toString(), "Не указана дата документа");
        Assert.notNull(documentDto.getSenders(), "Не указана сторона подписания (null).");
        Assert.notEmpty(documentDto.getSenders(), "Не указана сторона подписания (title).");
    }
}
