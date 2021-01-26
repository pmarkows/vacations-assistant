package com.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Holiday {

    private String name;
    private String date;

    @JsonProperty("public")
    private Boolean bankHoliday; // takie kiedy banki i urzędy są nieczynne

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean isBankHoliday() {
        return bankHoliday;
    }

    public void setBankHoliday(Boolean bankHoliday) {
        this.bankHoliday = bankHoliday;
    }
}
