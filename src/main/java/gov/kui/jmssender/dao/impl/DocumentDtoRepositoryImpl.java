package gov.kui.jmssender.dao.impl;

import com.hazelcast.collection.IList;
import gov.kui.jmssender.dao.DocumentDtoRepository;
import gov.kui.jmssender.model.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentDtoRepositoryImpl implements DocumentDtoRepository {

    private final IList<DocumentDto> hazelcastDocumentDtoList;

    @Autowired
    public DocumentDtoRepositoryImpl(IList<DocumentDto> hazelcastDocumentDtoList) {
        this.hazelcastDocumentDtoList = hazelcastDocumentDtoList;
    }

    @Override
    public boolean addDto(DocumentDto documentDto) {
        return hazelcastDocumentDtoList.add(documentDto);
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        return hazelcastDocumentDtoList;
    }
}
