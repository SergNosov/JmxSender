package gov.kui.jmssender.dao.impl;

import com.hazelcast.collection.IList;
import com.hazelcast.flakeidgen.FlakeIdGenerator;
import com.hazelcast.map.IMap;
import gov.kui.jmssender.dao.DocumentDtoRepository;
import gov.kui.jmssender.model.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentDtoRepositoryImpl implements DocumentDtoRepository {

    private final IList<DocumentDto> hazelcastDocumentDtoList;
    private final IMap<Long, DocumentDto> hazelcastDocumentDtoMap;
    private final FlakeIdGenerator flakeIdGenerator;

    @Autowired
    public DocumentDtoRepositoryImpl(IList<DocumentDto> hazelcastDocumentDtoList,
                                     IMap<Long, DocumentDto> hazelcastDocumentDtoMap,
                                     FlakeIdGenerator flakeIdGenerator) {
        this.hazelcastDocumentDtoList = hazelcastDocumentDtoList;
        this.hazelcastDocumentDtoMap = hazelcastDocumentDtoMap;
        this.flakeIdGenerator = flakeIdGenerator;
    }

    @Override
    public Optional<DocumentDto> addDto(DocumentDto documentDto) {
        // todo провести изменение - так делать нельзя
        final Long keyDto = flakeIdGenerator.newId();
        hazelcastDocumentDtoMap.put(keyDto, documentDto);
        return Optional.of(hazelcastDocumentDtoMap.get(keyDto));
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        return List.copyOf(hazelcastDocumentDtoMap.values());
    }
}
