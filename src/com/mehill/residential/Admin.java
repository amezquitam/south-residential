package com.mehill.residential;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Admin {
    private final List<Payment> payments;

    public Admin() {
        this.payments = new ArrayList<>(10);
    }

    public void pay(Apartment apartment, LocalDate date) {
        // Initial price depends in the floor number
        Double initialPrice = priceByFloor(apartment.getFloor());

        Integer dayOfTheMonth = date.getDayOfMonth();

        Double discount = dayOfTheMonth <= 10 ? 0.1 : 0.0;

        Double finalPrice = initialPrice * (1 - discount);

        Payment payment = new Payment(apartment, finalPrice, date);

        payments.add(payment);
    }

    private Double priceByFloor(Integer floor) {
        if (floor == 1) {
            return 200_000.0;
        }

        else if (2 <= floor && floor <= 9) {
            return 240_000.0;
        }

        // Else floor must be 10
        else {
            return 300_000.0;
        }
    }

    public Double calculateTotalPaymentByMonth(Integer year, Integer month) {
        return payments.stream()
                .filter(payment -> payment.getDatetime().getYear() == year &&
                        payment.getDatetime().getMonthValue() == month)
                .collect(Collectors.summingDouble(payment -> payment.getAmount()));
    }
}
