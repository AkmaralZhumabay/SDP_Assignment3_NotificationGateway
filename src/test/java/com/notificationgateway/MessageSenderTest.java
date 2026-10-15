package com.notificationgateway;

import com.notificationgateway.sender.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageSenderTest {

    @Test
    void telegramSuccessfulDelivery() {
        TelegramMessageSender telegram = new TelegramMessageSender();

        assertTrue(telegram.sendMessage(
                "user123", "Test", "Hello!"
        ));
    }

    @Test
    void telegramUnavailable() {
        TelegramMessageSender telegram = new TelegramMessageSender();
        telegram.setAvailable(false);

        assertFalse(telegram.isAvailable());
        assertFalse(telegram.sendMessage(
                "user123", "Test", "Hello!"
        ));
    }

    @Test
    void emailSuccessfulDelivery() {
        EmailMessageSender email = new EmailMessageSender();

        assertTrue(email.sendMessage(
                "user@example.com", "Test", "Hello!"
        ));
    }

    @Test
    void smsAccepts160Characters() {
        SmsMessageSender sms = new SmsMessageSender();

        String message = "A".repeat(160);

        assertTrue(sms.sendMessage(
                "+77001234567", "Test", message
        ));
    }

    @Test
    void smsRejectsOver160Characters() {
        SmsMessageSender sms = new SmsMessageSender();

        String message = "A".repeat(161);

        assertFalse(sms.sendMessage(
                "+77001234567", "Test", message
        ));
    }

    @Test
    void kafkaSuccessfulPublishing() {
        KafkaEventSender kafka = new KafkaEventSender();

        assertTrue(kafka.sendMessage(
                "user123", "OrderCreated", "Order placed"
        ));
    }

    @Test
    void pushSuccessfulDelivery() {
        PushNotificationSender push = new PushNotificationSender();

        assertTrue(push.sendMessage(
                "device123", "Test", "Hello!"
        ));
    }
}
