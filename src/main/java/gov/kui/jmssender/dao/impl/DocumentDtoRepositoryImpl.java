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
    public void addDtoToMap(final String key, final DocumentDto documentDto) {
        Assert.notNull(documentDto, "документ при записи в IMap не может быть null");
        Assert.notNull(key, "ключ(key) при записи в IMap не может быть null");

        if (!this.existsByKey(key)){
            hazelcastDocumentDtoMap.put(key, documentDto);
        }
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        return List.copyOf(hazelcastDocumentDtoMap.values());
    }

    @Override
    public boolean existsByKey(final String key) {
        Assert.notNull(key, "existsByKey: ключ(key) не может быть null.");
        return hazelcastDocumentDtoMap.containsKey(key);
    }
}
