package com.example.demo1.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.Session;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@ApplicationScoped
public class OrderWebSocketService {

    private static final CopyOnWriteArraySet<Session> sessions = new CopyOnWriteArraySet<>();

    // Додає сесію підключеного клієнта
    public void addSession(Session session) {
        sessions.add(session);
    }

    // Видаляє сесію після відключення клієнта
    public void removeSession(Session session) {
        sessions.remove(session);
    }

    // Надсилає повідомлення всім підключеним клієнтам
    public void sendMessageToAll(String message) {
        for (Session session : sessions) {
            if (session.isOpen()) {
                session.getAsyncRemote().sendText(message);
            }
        }
    }

    // Закриває всі сесії
    public void closeAllSessions() {
        for (Session session : sessions) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sessions.clear();
    }
}
