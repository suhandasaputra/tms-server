package com.bopro.parameter;

public class Notification {
    private String notification_id;
    private String title;
    private String date_create;
    private String email;
    private String notification_status;
    private String company_id;

    public Notification(String notification_id, String title, String date_create, String email, String notification_status, String company_id) {
        this.notification_id = notification_id;
        this.title = title;
        this.date_create = date_create;
        this.email = email;
        this.notification_status = notification_status;
        this.company_id = company_id;
    }

    public Notification() {
    }

    public String getNotificationId() {
        return notification_id;
    }

    public void setNotificationId(String notification_id) {
        this.notification_id = notification_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateCreate() {
        return date_create;
    }

    public void setDateCreate(String date_create) {
        this.date_create = date_create;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotificationStatus() {
        return notification_status;
    }

    public void setNotificationStatus(String notification_status) {
        this.notification_status = notification_status;
    }
    
    public String getCompanyId() {
        return company_id;
    }

    public void setCompanyId(String company_id) {
        this.company_id = company_id;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notification_id='" + notification_id + '\'' +
                ", title='" + title + '\'' +
                ", date_create='" + date_create + '\'' +
                ", email='" + email + '\'' +
                ", notification_status='" + notification_status + '\'' +
                ", company_id='" + company_id + '\'' +
                '}';
    }

    public void sendNotification() {
        System.out.println("Mengirim notifikasi: " + this.toString());
    }
}
