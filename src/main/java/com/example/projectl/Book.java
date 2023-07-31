package com.example.projectl;

import java.time.LocalDate;

public class Book {
    private int id;
    private String name;
    private String author;
    private LocalDate  date;
    private float price;

    public Book(int id, String name, String author, LocalDate  date, float price) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.date = date;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate  getDate() {
        return date;
    }

    public void setDate(LocalDate  date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
