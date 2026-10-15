package com.notificationgateway;

import com.notificationgateway.notification.Notification;
import com.notificationgateway.notification.UrgentNotification;
import com.notificationgateway.sender.TelegramMessageSender;
import com.notificationgateway.sender.EmailMessageSender;

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
    }
}
