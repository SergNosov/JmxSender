package gov.kui.jmssender.service.impl;

import gov.kui.jmssender.dao.DocumentDtoRepository;
import gov.kui.jmssender.model.DocumentDto;
import gov.kui.jmssender.service.DocumentDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class DocumentDtoServiceImpl implements DocumentDtoService {

    private final DocumentDtoRepository documentDtoRepository;

    @Autowired
    public DocumentDtoServiceImpl(DocumentDtoRepository documentDtoRepository) {
        this.documentDtoRepository = documentDtoRepository;
    }

    @Override
    public void sendDto(final DocumentDto documentDto) {
        this.checkDocumentDto(documentDto);
        documentDtoRepository.addDtoToMap(this.generateKey(documentDto), documentDto);
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        return documentDtoRepository.getAllDtos();
    }

    private void checkDocumentDto(final DocumentDto documentDto) {
        Assert.notNull(documentDto, "документ не может быть null");
        Assert.notNull(documentDto.getDoctype(), "Не указан тип документа.");
        Assert.hasText(documentDto.getDoctype().getTitle(), "Не указан заголовок документа.");
        Assert.hasText(documentDto.getNumber(), "Не указан номер документа.");
        Assert.notNull(documentDto.getDocDate(), "Не указана дата документа (null)");
        Assert.hasText(documentDto.getDocDate().toString(), "Не указана дата документа");
        Assert.notNull(documentDto.getSender(), "Не указана сторона подписания (null).");
        Assert.hasText(documentDto.getSender().getTitle(), "Не указана сторона подписания (title).");
    }

    private String generateKey(final DocumentDto documentDto) {
        return new StringBuilder(documentDto.getDoctype().getTitle())
                .append(";")
                .append(documentDto.getNumber())
                .append(";")
                .append(documentDto.getDocDate())
                .toString();
    }
}
