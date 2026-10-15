package com.notificationgateway.notification;

import com.notificationgateway.sender.MessageSender;

public class UrgentNotification extends Notification {

    private MessageSender fallbackSender;

    public UrgentNotification(MessageSender sender) {
        super(sender);
    }

    public void setFallbackSender(MessageSender fallbackSender) {
        this.fallbackSender = fallbackSender;
    }

    @Override
    public void send(String recipient, String message) {
        String urgentMessage = "[URGENT] " + message;

        if (sender.isAvailable()) {
            sender.sendMessage(recipient, "Urgent Notification", urgentMessage);
        } else if (fallbackSender != null && fallbackSender.isAvailable()) {
            System.out.println("Primary provider unavailable. Using fallback: "
                    + fallbackSender.getChannelName());

            fallbackSender.sendMessage(recipient, "Urgent Notification", urgentMessage);
        } else {
            System.out.println("Notification failed: no available provider.");
        }
    }
}
