package com.example.demo;

import jakarta.persistence.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String message;
    @Enumerated(EnumType.STRING)
    private AccessModifier accessModifier = AccessModifier.Public;
    @Column(unique = true)
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;
    private Date validUntil;

    public Message() {
    }

    public Message(User author, String message, AccessModifier accessModifier, Date validUntil) {
        this.author = author;
        this.message = message;
        this.accessModifier = accessModifier;
        this.url = UUID.randomUUID().toString();
        this.validUntil = validUntil;
    }

    public String getMessage() {
        return message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AccessModifier getAccessModifier() {
        return accessModifier;
    }

    public void setAccessModifier(AccessModifier accessModifier) {
        this.accessModifier = accessModifier;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getValidUntil() {
        return validUntil;
    }

    public void setValidUntil(Date validUntil) {
        this.validUntil = validUntil;
    }
}
