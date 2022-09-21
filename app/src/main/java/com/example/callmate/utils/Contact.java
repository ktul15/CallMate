package com.example.callmate.utils;

import java.util.ArrayList;

public class Contact {
    private Integer subscriberId;
    private String date_created;
    private String name;
    private String mainPhoneNumber;
    private ArrayList<String> alternate_phone_numbers = new ArrayList<>();
    private String employeeName;
    private String remarks;

    public Contact(Integer subscriberId, String date_created, String name, String mainPhoneNumber, ArrayList<String> alternate_phone_numbers, String employeeName, String remarks) {
        this.subscriberId = subscriberId;
        this.date_created = date_created;
        this.name = name;
        this.mainPhoneNumber = mainPhoneNumber;
        this.alternate_phone_numbers = alternate_phone_numbers;
        this.employeeName = employeeName;
        this.remarks = remarks;
    }

    public Contact() {
    }

    public void setSubscriberId(Integer subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Integer getSubscriberId(){
        return this.subscriberId;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setMainPhoneNumber(String mainPhoneNumber) {
        this.mainPhoneNumber = mainPhoneNumber;
    }

    public String getMainPhoneNumber() {
        return mainPhoneNumber;
    }

    public void setAlternate_phone_numbers(ArrayList<String> alternate_phone_numbers) {
        this.alternate_phone_numbers = alternate_phone_numbers;
    }

    public ArrayList<String> getAlternate_phone_numbers() {
        return alternate_phone_numbers;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getRemarks() {
        return remarks;
    }
}
