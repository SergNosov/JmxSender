package gov.kui.jmssender.dao.impl;

import com.hazelcast.collection.IList;
import com.hazelcast.map.IMap;
import gov.kui.jmssender.dao.DocumentDtoRepository;
import gov.kui.jmssender.model.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentDtoRepositoryImpl implements DocumentDtoRepository {
    private final IList<DocumentDto> hazelcastDocumentDtoList;
    private final IMap<String, DocumentDto> hazelcastDocumentDtoMap;

    @Autowired
    public DocumentDtoRepositoryImpl(IList<DocumentDto> hazelcastDocumentDtoList,
                                     IMap<String, DocumentDto> hazelcastDocumentDtoMap) {
        this.hazelcastDocumentDtoList = hazelcastDocumentDtoList;
        this.hazelcastDocumentDtoMap = hazelcastDocumentDtoMap;
    }

    @Override
    public Optional<DocumentDto> addDto(final DocumentDto documentDto) {

        Assert.notNull(documentDto, "документ не может быть null");
        Assert.notNull(documentDto.getDoctype(), "Не указан тип документа.");
        Assert.hasText(documentDto.getDoctype().getTitle(), "Не указан заголовок документа.");
        Assert.hasText(documentDto.getNumber(), "Не указан номер документа.");
        Assert.notNull(documentDto.getDocDate(), "Не указана дата документа (null)");
        Assert.hasText(documentDto.getDocDate().toString(), "Не указана дата документа");
        Assert.notNull(documentDto.getSender(), "Не указана сторона подписания (null).");
        Assert.hasText(documentDto.getSender().getTitle(), "Не указана сторона подписания (title).");

        String keyDto = new StringBuilder(documentDto.getDoctype().getTitle())
                .append(";")
                .append(documentDto.getNumber())
                .append(";")
                .append(documentDto.getDocDate())
                .toString();
        //todo разобраться...
        //hazelcastDocumentDtoMap.put(keyDto, documentDto);

        DocumentDto documentDtoClone = hazelcastDocumentDtoMap.put(keyDto, documentDto);
        if (documentDtoClone == null) {
            return Optional.of(hazelcastDocumentDtoMap.get(keyDto));
        }
        return Optional.of(documentDtoClone);
//
//        DocumentDto documentDtoClone = hazelcastDocumentDtoMap.get(keyDto);
//
//        if (documentDtoClone == null) {
//            hazelcastDocumentDtoMap.put(keyDto, documentDto);
//            return Optional.of(hazelcastDocumentDtoMap.get(keyDto));
//        }
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        return List.copyOf(hazelcastDocumentDtoMap.values());
    }
}
