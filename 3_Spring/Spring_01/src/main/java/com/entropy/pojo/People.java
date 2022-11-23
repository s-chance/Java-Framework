package com.entropy.pojo;

import java.util.*;

public class People {
    private String name;
    private Address address;
    private String[] books;
    private List<String> works;
    private Map<String, String> cards;
    private Set<String> games;
    private String money;
    private Properties info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String[] getBooks() {
        return books;
    }

    public void setBooks(String[] books) {
        this.books = books;
    }

    public List<String> getWorks() {
        return works;
    }

    public void setWorks(List<String> works) {
        this.works = works;
    }

    public Map<String, String> getCards() {
        return cards;
    }

    public void setCards(Map<String, String> cards) {
        this.cards = cards;
    }

    public Set<String> getGames() {
        return games;
    }

    public void setGames(Set<String> games) {
        this.games = games;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public Properties getInfo() {
        return info;
    }

    public void setInfo(Properties info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "People{" +
                "name='" + name + '\'' +
                ", address=" + address +
                ", books=" + Arrays.toString(books) +
                ", works=" + works +
                ", cards=" + cards +
                ", games=" + games +
                ", money='" + money + '\'' +
                ", info=" + info +
                '}';
    }
}
