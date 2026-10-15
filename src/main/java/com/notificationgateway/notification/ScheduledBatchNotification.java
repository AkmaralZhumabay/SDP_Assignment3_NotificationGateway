package com.notificationgateway.notification;

import com.notificationgateway.sender.MessageSender;
import java.util.List;

public class ScheduledBatchNotification extends Notification {

    private List<String> recipients;

    public ScheduledBatchNotification(MessageSender sender, List<String> recipients) {
        super(sender);
        this.recipients = recipients;
    }

    @Override
    public void send(String recipient, String message) {
        int successful = 0;
        int failed = 0;

        for (String user : recipients) {
            boolean result = sender.sendMessage(
                    user,
                    "Batch Notification",
                    message
            );

            if (result) {
                successful++;
            } else {
                failed++;
            }
        }

        System.out.println("=== Batch Summary ===");
        System.out.println("Total: " + recipients.size());
        System.out.println("Successful: " + successful);
        System.out.println("Failed: " + failed);
        System.out.println("=====================");
    }
}
