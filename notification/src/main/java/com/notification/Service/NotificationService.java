package com.notification.Service;
import com.notification.Entity.Notification;
import com.notification.Repository.NotificationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 0 8 * * *") // Tâche quotidienne à 8h
    public void sendLateBorrowNotifications() {
        String borrowServiceUrl = "http://localhost:8081/borrows/late";

        // Appel à Borrow Service pour récupérer les emprunts en retard
        ResponseEntity<List> response = restTemplate.exchange(borrowServiceUrl, HttpMethod.GET, null, List.class);
        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<?> borrows = response.getBody();

            for (Object borrow : borrows) {
                Map<String, Object> borrowData = (Map<String, Object>) borrow;
                Long userId = Long.valueOf(borrowData.get("userId").toString());
                Long bookId = Long.valueOf(borrowData.get("bookId").toString());
                String bookName = borrowData.get("bookName").toString();

                // Récupérer les informations de l'utilisateur via Authentication Service
                String authServiceUrl = "http://localhost:8083/users/api" + userId;
                User user = restTemplate.getForObject(authServiceUrl, User.class);

                if (user != null) {
                    // Construire le message de retard
                    String message = String.format("Bonjour %s, votre emprunt du livre '%s' (ID: %d) est en retard. Merci de le retourner rapidement.",
                            user.getName(), bookName, bookId);

                    // Envoyer l'e-mail
                    emailService.sendEmail(user.getEmail(), "Rappel : Emprunt en retard", message);

                    // Enregistrer la notification sans changer l'état de lecture
                    Notification notification = new Notification(userId, bookId, bookName, message);
                    notificationRepository.save(notification);

                    System.out.println("E-mail envoyé à : " + user.getEmail() + " | Message : " + message);
                }
            }
        }
    }

    static class User {
        private String name;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}