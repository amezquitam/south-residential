package com.mehill.residential;
import java.time.LocalDate;

public class Payment {
    private final Apartment apartment;
    private final Double amount;
    private final LocalDate datetime;

    public Payment(Apartment apartment, Double amount, LocalDate datetime) {
        this.apartment = apartment;
        this.amount = amount;
        this.datetime = datetime;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDate getDatetime() {
        return datetime;
    }
}