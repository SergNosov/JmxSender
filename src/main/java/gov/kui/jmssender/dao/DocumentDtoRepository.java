package gov.kui.jmssender.dao;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;

public interface DocumentDtoRepository {
    public boolean addDto(DocumentDto documentDto);
    public List<DocumentDto> getAllDtos();
}
