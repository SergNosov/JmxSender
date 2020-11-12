package gov.kui.jmssender.service;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;
import java.util.Optional;

public interface DocumentDtoService {
    /**
     * используется для отправки DTO-объекта брокеру сообщений.
     *
     *@param documentDto DTO-объект для отправки брокеру сообщений.
     *@return Optional<DocumentDto>, если возвращаемое значение isEmpty() - то такой DTO-объекта отправлялся ранее.
     */
    public Optional<DocumentDto> sendDto(DocumentDto documentDto);

    /**
     * используется для получение списка DTO-объектов отправленных через брокер сообщений.
     *
     *@return список DTO-объектов отправленных через брокер сообщений.
     */
    public List<DocumentDto> getAllDtos();
}
