
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
        System.out.println("\n=== Scenario 1: Urgent + Telegram ===");

        Notification urgent = new UrgentNotification(telegram);
        urgent.send("user123", "Server is down!");

        // Scenario 2: EncryptedNotification + Email
        System.out.println("\n=== Scenario 2: Encrypted + Email ===");

        Notification encrypted = new EncryptedNotification(email);
        encrypted.send("user@example.com", "Confidential message");

        // Scenario 3: ScheduledBatchNotification + SMS
        System.out.println("\n=== Scenario 3: Batch + SMS ===");

        List<String> recipients = List.of(
                "+77001111111",
                "+77002222222",
                "+77003333333"
        );

        Notification batch = new ScheduledBatchNotification(sms, recipients);
        batch.send("", "Meeting at 10 AM");

        // Scenario 4: Runtime switching Telegram -> Email
        System.out.println("\n=== Scenario 4: Runtime Switching ===");

        Notification switching = new UrgentNotification(telegram);

        switching.send("user123", "System alert!");

        switching.setSender(email);

        switching.send("user@example.com", "System alert!");

        // Scenario 5: Telegram unavailable -> SMS fallback
        System.out.println("\n=== Scenario 5: Fallback to SMS ===");

        telegram.setAvailable(false);

        UrgentNotification fallback = new UrgentNotification(telegram);
        fallback.setFallbackSender(sms);

        fallback.send("+77001234567", "Critical system failure!");

        // Scenario 6: MarketingNotification + PushNotificationSender
        System.out.println("\n=== Scenario 6: Marketing + Push ===");

        Notification marketing = new MarketingNotification(push);
        marketing.send("device123", "Special discount available!");
    }
}
