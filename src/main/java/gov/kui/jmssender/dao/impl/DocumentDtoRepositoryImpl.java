package gov.kui.jmssender.dao.impl;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import gov.kui.jmssender.dao.DocumentDtoRepository;
import gov.kui.jmssender.model.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentDtoRepositoryImpl implements DocumentDtoRepository {

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public DocumentDtoRepositoryImpl(@Qualifier("hazelcastInstance") HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    @Override
    public boolean addDto(DocumentDto documentDto) {
        return hazelcastInstance.getList("documentDtoList").add(documentDto);
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        return hazelcastInstance.getList("documentDtoList");
    }
}
