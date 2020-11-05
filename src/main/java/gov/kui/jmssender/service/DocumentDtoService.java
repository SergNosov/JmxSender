package gov.kui.jmssender.service;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;

public interface DocumentDtoService {
    public void sendDto(DocumentDto documentDto);
    public List<DocumentDto> getAllDtos();
}
