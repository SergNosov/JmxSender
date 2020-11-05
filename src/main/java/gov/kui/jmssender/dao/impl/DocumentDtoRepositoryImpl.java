package gov.kui.jmssender.dao.impl;

import com.hazelcast.map.IMap;
import gov.kui.jmssender.dao.DocumentDtoRepository;
import gov.kui.jmssender.model.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class DocumentDtoRepositoryImpl implements DocumentDtoRepository {
    private final IMap<String, DocumentDto> hazelcastDocumentDtoMap;

    @Autowired
    public DocumentDtoRepositoryImpl(IMap<String, DocumentDto> hazelcastDocumentDtoMap) {
        this.hazelcastDocumentDtoMap = hazelcastDocumentDtoMap;
    }

    @Override
    public void addDto(final DocumentDto documentDto) {
        checkDocumentDto(documentDto);
        final String keyDto = generateKey(documentDto);
        putDtoToMap(keyDto,documentDto);
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

    private void putDtoToMap(final String keyDto, final DocumentDto documentDto) {
        Assert.notNull(documentDto, "документ при записи в IMap не может быть null");
        Assert.notNull(keyDto, "ключь(key) при записи в IMap не может быть null");

        if (!hazelcastDocumentDtoMap.containsKey(keyDto)){
            hazelcastDocumentDtoMap.put(keyDto, documentDto);
        }
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        return List.copyOf(hazelcastDocumentDtoMap.values());
    }
}
