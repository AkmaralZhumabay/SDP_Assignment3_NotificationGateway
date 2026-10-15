package com.notificationgateway;

import com.notificationgateway.notification.*;
import com.notificationgateway.sender.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {

    private String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try {
            System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
            action.run();
        } finally {
            System.setOut(originalOut);
        }

        return output.toString(StandardCharsets.UTF_8);
    }

    @Test
    void urgentNotificationUsesFallback() {
        TelegramMessageSender telegram = new TelegramMessageSender();
        telegram.setAvailable(false);

        SmsMessageSender sms = new SmsMessageSender();

        UrgentNotification notification = new UrgentNotification(telegram);
        notification.setFallbackSender(sms);

        String output = captureOutput(() ->
                notification.send("+77001234567", "Server down!")
        );

        assertTrue(output.contains("Using fallback: SMS"));
        assertTrue(output.contains("[URGENT] Server down!"));
    }

    @Test
    void encryptedNotificationTransformsMessage() {
        EmailMessageSender email = new EmailMessageSender();
        EncryptedNotification notification = new EncryptedNotification(email);

        String output = captureOutput(() ->
                notification.send("user@example.com", "Hello")
        );

        assertTrue(output.contains("SGVsbG8="));
        assertFalse(output.contains("\nHello\n"));
    }

    @Test
    void batchNotificationProducesSummary() {
        SmsMessageSender sms = new SmsMessageSender();

        List<String> recipients = List.of(
                "+77001111111",
                "+77002222222",
                "+77003333333"
        );

        ScheduledBatchNotification notification =
                new ScheduledBatchNotification(sms, recipients);

        String output = captureOutput(() ->
                notification.send("", "Meeting at 10 AM")
        );

        assertTrue(output.contains("Total: 3"));
        assertTrue(output.contains("Successful: 3"));
        assertTrue(output.contains("Failed: 0"));
    }

    @Test
    void notificationSupportsRuntimeSwitching() {
        TelegramMessageSender telegram = new TelegramMessageSender();
        EmailMessageSender email = new EmailMessageSender();

        Notification notification = new UrgentNotification(telegram);

        String telegramOutput = captureOutput(() ->
                notification.send("user123", "Alert!")
        );

        notification.setSender(email);

        String emailOutput = captureOutput(() ->
                notification.send("user@example.com", "Alert!")
        );

        assertTrue(telegramOutput.contains("Telegram Message"));
        assertTrue(emailOutput.contains("Email Message"));
    }

    @Test
    void marketingNotificationWorksWithEmail() {
        EmailMessageSender email = new EmailMessageSender();
        MarketingNotification notification = new MarketingNotification(email);

        String output = captureOutput(() ->
                notification.send("user@example.com", "Special offer!")
        );

        assertTrue(output.contains("Marketing Notification"));
        assertTrue(output.contains("[MARKETING] Special offer!"));
    }
}
