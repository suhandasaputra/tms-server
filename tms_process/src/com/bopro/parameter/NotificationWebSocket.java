/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bopro.parameter;

/**
 *
 * @author azzam
 */
import javax.websocket.server.ServerEndpoint;
import javax.websocket.*;

@ServerEndpoint("/notification")
public class NotificationWebSocket {

    @OnOpen
    public void onOpen(Session session) {
        // Menambahkan session WebSocket ke daftar session yang terhubung
        WebSocketManager.addSession(session);
    }

    @OnClose
    public void onClose(Session session) {
        // Menghapus session WebSocket dari daftar session yang terhubung
        WebSocketManager.removeSession(session);
    }

    public static void sendNotification(String message) {
        // Mengirimkan notifikasi ke semua session yang terhubung
        WebSocketManager.broadcast(message);
    }
}
