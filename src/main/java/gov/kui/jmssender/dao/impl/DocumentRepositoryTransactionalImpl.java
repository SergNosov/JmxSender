package gov.kui.jmssender.dao.impl;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.hazelcast.transaction.HazelcastXAResource;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionalMap;
import gov.kui.jmssender.dao.DocumentDtoRepository;
import gov.kui.jmssender.model.DocumentDto;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.Transactional;
import java.util.List;

public class DocumentRepositoryTransactionalImpl implements DocumentDtoRepository {

    @Autowired
    private HazelcastXAResource hazelcastXAResource;

    @Autowired
    private UserTransactionManager userTransactionManager;

    @Override
    @Transactional
    public DocumentDto save(DocumentDto documentDto) {

        try {
            final Transaction transaction = userTransactionManager.getTransaction();
            transaction.enlistResource(hazelcastXAResource);

            final TransactionContext hzTransactionContext = hazelcastXAResource.getTransactionContext();

            final TransactionalMap<String, DocumentDto> hazelcastDocumentDtoMap = hzTransactionContext.getMap("documentDtoMap"); //todo !!!!!

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
