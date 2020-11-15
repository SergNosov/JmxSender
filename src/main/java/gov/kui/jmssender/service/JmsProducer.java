package gov.kui.jmssender.service;

import gov.kui.jmssender.model.DocumentDto;

public interface JmsProducer {
    public void send(DocumentDto documentDto);
}
