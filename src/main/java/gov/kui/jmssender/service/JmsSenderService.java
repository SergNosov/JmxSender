package gov.kui.jmssender.service;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;
import java.util.Optional;

public interface JmsSenderService {

    /**
     * используется для передачи DTO-объекта брокеру сообщений,
     * с сохранением во временном хранилище отправленных  DTO-объектов.
     *
     *@param documentDto DTO-объект для передачи и сохранения.
     *@return Optional<DocumentDto>, если возвращаемое значение isEmpty() - то такой DTO-объекта отправлялся ранее
     * и присутствует во временном хранилище;
     */
    Optional<DocumentDto> sendMessage(final DocumentDto documentDto);

    /**
     * используется для получение списка всех DTO-объектов из временного хранилища.
     *
     *@return список DTO-объектов;
     */
    List<DocumentDto> getAllDtos();

    /**
     * Используется для проверки доступности брокера сообщений. // todo правильно сформулировать задачу о перверке доступности частей обработки данных
     *
     */
    void isAvailable();
}
