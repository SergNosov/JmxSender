package gov.kui.jmssender.dao;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;
import java.util.Optional;

public interface DocumentDtoRepository {
    public Optional<DocumentDto> addDto(DocumentDto documentDto);
    public List<DocumentDto> getAllDtos();
}
