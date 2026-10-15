package com.notificationgateway;

import com.notificationgateway.sender.SmsMessageSender;
import com.notificationgateway.notification.Notification;
import com.notificationgateway.notification.UrgentNotification;
import com.notificationgateway.sender.TelegramMessageSender;
import com.notificationgateway.sender.EmailMessageSender;
import com.notificationgateway.sender.PushNotificationSender;

public class Main {

    public static void main(String[] args) {

        TelegramMessageSender telegram = new TelegramMessageSender();
        EmailMessageSender email = new EmailMessageSender();

        Notification notification = new UrgentNotification(telegram);

        System.out.println("=== Sending via Telegram ===");
        notification.send("user123", "Server is down!");

        notification.setSender(email);

        System.out.println("\n=== Switching to Email ===");
        notification.send("user@example.com", "Server is down!");

        System.out.println("\n=== Fallback: Telegram to SMS ===");

        TelegramMessageSender unavailableTelegram = new TelegramMessageSender();
        SmsMessageSender sms = new SmsMessageSender();

        unavailableTelegram.setAvailable(false);

        UrgentNotification urgent = new UrgentNotification(unavailableTelegram);
        urgent.setFallbackSender(sms);

        urgent.send("+77001234567", "Server is down!");

        System.out.println("\n=== Urgent Notification via Push ===");

        PushNotificationSender push = new PushNotificationSender();

        Notification pushNotification = new UrgentNotification(push);
        pushNotification.send("device123", "Security alert!");

    }
}
