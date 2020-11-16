package gov.kui.jmssender.service;

import gov.kui.jmssender.model.DocumentDto;

public interface JmsProducerService {
    public void send(DocumentDto documentDto);
    public void isJmsAlive();
}
