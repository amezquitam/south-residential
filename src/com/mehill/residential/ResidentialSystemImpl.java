package com.mehill.residential;

import java.time.LocalDate;
import java.util.List;

public class ResidentialSystemImpl implements ResidentialSystem {

    Residential residential;

    public ResidentialSystemImpl() {
        this.residential = new Residential(new Admin());
    }

    @Override
    public void pay(String apartmentId, LocalDate payDate) {
        residential.pay(apartmentId, payDate);
    }

    @Override
    public void registApartmentWithOwner(Apartment apartment) {
        residential.addApartmentWithOwner(apartment);        
    }

    @Override
    public List<String> getApartmentsByOwner(String owner) {
        return residential.getApartmentsByOwner(owner);
    }

    public Double calculateTotalPaymentByMonth(Integer year, Integer month) {
        return residential.calculateTotalPaymentByMonth(year, month);
    }

    @Override
    public Double getPercentageOfEmptyApartments() {
        return residential.getPercentageOfEmptyApartments();
    }

    @Override
    public Double getPercentageOfRentedApartments() {
        return residential.getPercentageOfRentedApartments();
    }

}
