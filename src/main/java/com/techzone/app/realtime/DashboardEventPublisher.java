package com.techzone.app.realtime;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DashboardEventPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public DashboardEventPublisher(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Scheduled(fixedRate = 15000)
    public void sendDashboardEvent() {
        String payload = "Dashboard event @ " + LocalDateTime.now();
        messagingTemplate.convertAndSend("/topic/dashboard", payload);
    }
}
