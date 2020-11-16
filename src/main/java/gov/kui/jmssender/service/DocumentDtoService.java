package gov.kui.jmssender.service;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;
import java.util.Optional;

public interface DocumentDtoService {
    /**
     * используется для добавления DTO-объекта во временное хранилище переданных DTO.
     *
     *@param documentDto DTO-объект для сохранения.
     *@return Optional<DocumentDto>, если возвращаемое значение isEmpty() - то такой DTO-объект сохранялся ранее.
     */
    public Optional<DocumentDto> addDto(DocumentDto documentDto);

    /**
     * используется для получения из временного хранилища списка DTO-объектов сохраненных ранее.
     *
     *@return список DTO-объектов сохраненных ранее.
     */
    public List<DocumentDto> getAllDtos();

    public boolean isExists(DocumentDto documentDto);
}
