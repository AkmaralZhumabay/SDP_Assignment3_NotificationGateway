package com.notificationgateway;

import com.notificationgateway.notification.*;
import com.notificationgateway.sender.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        TelegramMessageSender telegram = new TelegramMessageSender();
        EmailMessageSender email = new EmailMessageSender();
        SmsMessageSender sms = new SmsMessageSender();
        PushNotificationSender push = new PushNotificationSender();

        // Scenario 1: UrgentNotification + Telegram
        Notification urgent = new UrgentNotification(telegram);
        urgent.send("user123", "Server is down!");

        System.out.println();

        // Scenario 2: EncryptedNotification + Email
        Notification encrypted = new EncryptedNotification(email);
        encrypted.send("user@example.com", "Confidential message");

        System.out.println();

        // Scenario 3: ScheduledBatchNotification + SMS
        List<String> recipients = List.of(
                "+77001111111",
                "+77002222222",
                "+77003333333"
        );

        Notification batch = new ScheduledBatchNotification(sms, recipients);
        batch.send("", "Meeting at 10 AM");

        System.out.println();

        // Scenario 4: Runtime switching Telegram -> Email
        Notification switching = new UrgentNotification(telegram);
        switching.send("user123", "System alert!");

        System.out.println();

        switching.setSender(email);
        switching.send("user@example.com", "System alert!");

        System.out.println();

        // Scenario 5: Telegram unavailable -> SMS fallback
        telegram.setAvailable(false);

        UrgentNotification fallback = new UrgentNotification(telegram);
        fallback.setFallbackSender(sms);
        fallback.send("+77001234567", "Critical system failure!");

        System.out.println();

        // Scenario 6: MarketingNotification + PushNotificationSender
        Notification marketing = new MarketingNotification(push);
        marketing.send("device123", "Special discount available!");
    }
}
