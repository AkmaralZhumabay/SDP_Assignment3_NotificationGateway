package com.notificationgateway.notification;

import com.notificationgateway.sender.MessageSender;

public class MarketingNotification extends Notification {

    public MarketingNotification(MessageSender sender) {
        super(sender);
    }

    @Override
    public void send(String recipient, String message) {
        String marketingMessage = "[MARKETING] " + message;

        sender.sendMessage(
                recipient,
                "Marketing Notification",
                marketingMessage
        );
    }
}
