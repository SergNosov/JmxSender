package gov.kui.jmssender.dao.impl;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.hazelcast.transaction.HazelcastXAResource;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionalMap;
import gov.kui.jmssender.dao.DocumentDtoRepository;
import gov.kui.jmssender.model.DocumentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class DocumentDtoRepositoryTransactionalImpl implements DocumentDtoRepository {

    private final HazelcastXAResource hazelcastXAResource;
    private final UserTransactionManager userTransactionManager;
    private final String hzInstanceName;

    public DocumentDtoRepositoryTransactionalImpl(HazelcastXAResource hazelcastXAResource,
                                                  UserTransactionManager userTransactionManager,
                                                  @Value("${jmssender.hazalcast.instanceName}")
                                                       String hzInstanceName) {
        this.hazelcastXAResource = hazelcastXAResource;
        this.userTransactionManager = userTransactionManager;
        this.hzInstanceName = hzInstanceName;
    }

    @Override
    @Transactional
    public DocumentDto save(DocumentDto documentDto) {

        try {
            final Transaction transaction = userTransactionManager.getTransaction();
            transaction.enlistResource(hazelcastXAResource);

            final TransactionContext hzTransactionContext = hazelcastXAResource.getTransactionContext();

            final TransactionalMap<String, DocumentDto> hazelcastDocumentDtoMap = hzTransactionContext.getMap(hzInstanceName);

            final String key = this.generateKey(documentDto);

            hazelcastDocumentDtoMap.set(key,documentDto);

            return documentDto;

        } catch (SystemException | RollbackException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        return null;
    }

    @Override
    public boolean isExists(DocumentDto documentDto) {
        return false;
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
