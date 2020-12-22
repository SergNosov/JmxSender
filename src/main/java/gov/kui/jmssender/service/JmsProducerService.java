package gov.kui.jmssender.service;

import gov.kui.jmssender.model.DocumentDto;

import java.util.List;

public interface JmsProducerService {
    /**
     * Используется для отправки DTO-объекта брокером сообщений.
     *
     * @param documentDto DTO-объект для отправки.
     */
    void send(final DocumentDto documentDto);

    /**
     * Используется для проверки доступности брокера сообщений.
     */
    void isJmsAlive();

    /**
     * используется для получение списка всех DTO-объектов из очереди брокера сообщений.
     *
     * @return список DTO-объектов;
     */
    List<DocumentDto> getAllMessages();
}
