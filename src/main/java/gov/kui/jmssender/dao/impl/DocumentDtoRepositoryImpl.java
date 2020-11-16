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
    public void addDtoToMap(final DocumentDto documentDto) {
        Assert.notNull(documentDto, "документ при записи в IMap не может быть null");

        final String key = this.generateKey(documentDto);
        Assert.notNull(key, "addDtoToMap: ключ(key) не может быть null.");

        if (!this.existsByKey(documentDto)){
            hazelcastDocumentDtoMap.put(key, documentDto);
        }
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        return List.copyOf(hazelcastDocumentDtoMap.values());
    }

    @Override
    public boolean existsByKey(final DocumentDto documentDto) {
        Assert.notNull(documentDto, "existsByKey: документ не может быть null");

        final String key = this.generateKey(documentDto);
        Assert.notNull(key, "existsByKey: ключ(key) не может быть null.");

        return hazelcastDocumentDtoMap.containsKey(key);
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
