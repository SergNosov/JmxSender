package gov.kui.jmssender.dao;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;

public interface DocumentDtoRepository {
    public void addDtoToMap(String key, DocumentDto documentDto);
    public List<DocumentDto> getAllDtos();
}
