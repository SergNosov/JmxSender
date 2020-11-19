package gov.kui.jmssender.dao;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;

public interface DocumentDtoRepository {
    public DocumentDto save(final DocumentDto documentDto);
    public List<DocumentDto> getAllDtos();
    public boolean isExists(final DocumentDto documentDto);
}
