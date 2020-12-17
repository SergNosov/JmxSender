package gov.kui.jmssender.service;

import gov.kui.jmssender.model.DocumentDto;

public interface JmsProducerService {
    /**
     * Используется для отправки DTO-объекта брокером сообщений.
     *
     *@param documentDto DTO-объект для отправки.
     */
    void send(final DocumentDto documentDto);

    /**
     * Используется для проверки доступности брокера сообщений.
     *
     */
    void isJmsAlive();
}
