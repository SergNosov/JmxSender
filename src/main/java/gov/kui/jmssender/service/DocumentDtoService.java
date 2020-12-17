package gov.kui.jmssender.service;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;

public interface DocumentDtoService {
    /**
     * используется для сохранения DTO-объекта в хранилище переданных DTO.
     *
     *@param documentDto DTO-объект для сохранения.
     *@return DocumentDto.
     */
    DocumentDto save(final DocumentDto documentDto);

    /**
     * используется для получения списка DTO-объектов сохраненных ранее.
     *
     *@return список DTO-объектов сохраненных ранее.
     */
    List<DocumentDto> getAllDtos();

    /**
     * используется для проверки наличия DTO-объекта в хранилище переданных DTO.
     *
     *@return true - DTO объект присутствует в хранилище (сохранялся ранее),
     * false - DTO объекта в хранилище нет.
     */
    boolean isExists(final DocumentDto documentDto);
}
