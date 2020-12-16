package gov.kui.jmssender.dao.impl;

import com.atomikos.icatch.jta.UserTransactionManager;
import com.hazelcast.transaction.HazelcastXAResource;
import com.hazelcast.transaction.TransactionContext;
import com.hazelcast.transaction.TransactionalMap;
import gov.kui.jmssender.dao.DocumentDtoRepository;
import gov.kui.jmssender.model.DocumentDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.xa.XAResource;

import java.util.List;

@Component
public class DocumentDtoRepositoryTransactionalImpl implements DocumentDtoRepository {

    private final HazelcastXAResource hazelcastXAResource;
    private final UserTransactionManager userTransactionManager;
    private final String hzIMapName;

    public DocumentDtoRepositoryTransactionalImpl(HazelcastXAResource hazelcastXAResource,
                                                  UserTransactionManager userTransactionManager,
                                                  @Value("${jmssender.hazalcast.IMapName}")
                                                       String hzIMapName) {
        this.hazelcastXAResource = hazelcastXAResource;
        this.userTransactionManager = userTransactionManager;
        this.hzIMapName = hzIMapName;
    }

    @Override
    public DocumentDto save(DocumentDto documentDto) {
        Assert.notNull(documentDto, "Документ при записи в IMap не может быть null");

        final String key = this.generateKey(documentDto);
        Assert.notNull(key, "Ключ(key) не может быть null.");

        try {
            return this.saveToMap(key,documentDto);
        } catch (SystemException | RollbackException e) {
            throw new RuntimeException("Ошибка при записи documentDto в  Hazelcast TransactionalMap: "+e);
        }
    }

    private DocumentDto saveToMap(String key, DocumentDto documentDto) throws SystemException, RollbackException {
        final Transaction transaction = userTransactionManager.getTransaction();
        transaction.enlistResource(hazelcastXAResource);

        final TransactionContext hzTransactionContext = hazelcastXAResource.getTransactionContext();

        final TransactionalMap<String, DocumentDto> hazelcastDocumentDtoMap = hzTransactionContext.getMap(hzIMapName);
        hazelcastDocumentDtoMap.set(key,documentDto); // todo определиться со способом записи в IMap, что возвращаем?

        transaction.delistResource(hazelcastXAResource, XAResource.TMSUCCESS);
        return documentDto;
    }

    @Override
    public List<DocumentDto> getAllDtos() {
        try {
            return this.getMapValues();
        } catch (SystemException | RollbackException e) {
            throw new RuntimeException("Ошибка получения списка documentDto в  DocumentDtoRepositoryTransactionalImpl: "+e);
        }
    }

    private List<DocumentDto> getMapValues() throws SystemException, RollbackException {
        final Transaction transaction = userTransactionManager.getTransaction();
        transaction.enlistResource(hazelcastXAResource);

        final TransactionContext hzTransactionContext = hazelcastXAResource.getTransactionContext();

        final TransactionalMap<String, DocumentDto> hazelcastDocumentDtoMap = hzTransactionContext.getMap(hzIMapName);
        List<DocumentDto> documentDtos = List.copyOf(hazelcastDocumentDtoMap.values());

        transaction.delistResource(hazelcastXAResource, XAResource.TMSUCCESS);
        return documentDtos;
    }

    @Override
    public boolean isExists(DocumentDto documentDto) {
        Assert.notNull(documentDto, "isExists: документ не может быть null");

        final String key = this.generateKey(documentDto);
        Assert.notNull(key, "existsByKey: ключ(key) не может быть null.");

        try {
            return this.isDtoExists(key);
        } catch (SystemException | RollbackException e) {
            throw new RuntimeException("Ошибка isExists в DocumentDtoRepositoryTransactionalImpl: "+e);
        }
    }

    private boolean isDtoExists(String dtoKey) throws SystemException, RollbackException {
        final Transaction transaction = userTransactionManager.getTransaction();
        transaction.enlistResource(hazelcastXAResource);

        final TransactionContext hzTransactionContext = hazelcastXAResource.getTransactionContext();
        final TransactionalMap<String, DocumentDto> hazelcastDocumentDtoMap = hzTransactionContext.getMap(hzIMapName);

        final boolean isExists = hazelcastDocumentDtoMap.containsKey(dtoKey);

        transaction.delistResource(hazelcastXAResource, XAResource.TMSUCCESS);
        return isExists;
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
