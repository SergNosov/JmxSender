package gov.kui.jmssender.service;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;
import java.util.Optional;

public interface DocumentDtoService {
    /**
     * используется для сохранения DTO-объекта в хранилище переданных DTO.
     *
     *@param documentDto DTO-объект для сохранения.
     *@return Optional<DocumentDto>, если возвращаемое значение isEmpty() - то такой DTO-объект сохранялся ранее.
     */
    public Optional<DocumentDto> save(DocumentDto documentDto);

    /**
     * используется для получения списка DTO-объектов сохраненных ранее.
     *
     *@return список DTO-объектов сохраненных ранее.
     */
    public List<DocumentDto> getAllDtos();

    /**
     * используется для проверки наличия DTO-объекта в хранилище переданных DTO.
     *
     *@return true - DTO объект присутствует в хранилище (сохранялся ранее),
     * false - DTO объекта в хранилище нет.
     */
    public boolean isExists(DocumentDto documentDto);
}
