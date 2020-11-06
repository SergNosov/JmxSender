package gov.kui.jmssender.dao;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;

public interface DocumentDtoRepository {
    public void addDtoToMap(final String key, final DocumentDto documentDto);
    public List<DocumentDto> getAllDtos();
    public boolean existsByKey(final String key);
}
