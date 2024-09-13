/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bopro.parameter;

/**
 *
 * @author azzam
 */
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class WebSocketManager {
    private static Set<Session> sessions = new HashSet<>();

    public static void addSession(Session session) {
        sessions.add(session);
    }

    public static void removeSession(Session session) {
        sessions.remove(session);
    }

    public static void broadcast(String message) {
        for (Session session : sessions) {
            sendToSession(session, message);
        }
    }

    private static void sendToSession(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}