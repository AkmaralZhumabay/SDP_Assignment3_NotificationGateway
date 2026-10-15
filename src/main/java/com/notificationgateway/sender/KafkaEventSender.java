package com.notificationgateway.sender;

public class KafkaEventSender implements MessageSender {

    private boolean available = true;

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public boolean sendMessage(String recipient, String title, String body) {
        if (!isAvailable()) {
            System.out.println("Kafka is unavailable.");
            return false;
        }

        System.out.println("=== Kafka Event ===");
        System.out.println("Topic: notifications");
        System.out.println("Recipient: " + recipient);
        System.out.println("Event: " + title);
        System.out.println("Payload: " + body);
        System.out.println("Event published successfully.");
        System.out.println("===================");

        return true;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public String getChannelName() {
        return "Kafka";
    }
}
