package com.mehill.residential;

import java.time.LocalDate;
import java.util.List;

public interface ResidentialSystem {
    
    void registApartmentWithOwner(Apartment apartment);

    void pay(String apartmentId, LocalDate payDate);

    List<String> getApartmentsByOwner(String owner);    

    Double calculateTotalPaymentByMonth(Integer year, Integer month);

    Double getPercentageOfRentedApartments();

    Double getPercentageOfEmptyApartments();
}
