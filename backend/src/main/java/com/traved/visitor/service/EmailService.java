package com.traved.visitor.service;

public interface EmailService {
    void sendSimpleMail(String to, String subject, String body);
}
