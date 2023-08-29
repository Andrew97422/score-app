package com.bigdata.application.service;

public interface EmailService {

    void sendSimpleEmail(final String toAddress, final String subject, final String message);
}
