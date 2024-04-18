package com.mehill.residential;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Residential {
    public final String name = "Brisas del sur";
    private final Admin admin;
    private List<Apartment> apartments;
    private List<Owner> owners;

    public Residential(Admin admin) {
        this.admin = admin;
        this.apartments = new ArrayList<>(10);
        this.owners = new ArrayList<>(10);
    }

    public void addApartmentWithOwner(Apartment apartment) {
        if (apartments.contains(apartment)) {
            throw new RuntimeException(
                    String.format("El apartamento con el número de piso %s ya se encuentra registrado",
                            apartment.getId()));
        }

        apartments.add(apartment);

        Owner ownerOnList = apartments.stream()
                .map(_apartment -> _apartment.getOwner())
                .filter(_owner -> _owner.equals(apartment.getOwner()))
                .findFirst().orElse(null);

        if (Objects.isNull(ownerOnList)) {
            owners.add(apartment.getOwner());
        } else {
            apartment.setOwner(ownerOnList);
        }
    }

    public void pay(String apartmentId, LocalDate date) {
        var apartment = apartments.stream()
                .filter(_apartment -> _apartment.getId().equals(apartmentId))
                .findFirst().orElse(null);

        if (Objects.isNull(apartment)) {
            throw new IllegalArgumentException("El numero de apartamento provisto no se encuentra registrado");
        }

        admin.pay(apartment, date);
    }

    public List<String> getApartmentsByOwner(String owner) {
        return apartments.stream()
            .filter(_apartment -> _apartment.getOwner().getName().equals(owner))
            .map(_apartment -> _apartment.getId())
            .collect(Collectors.toList());
    }

    public Double calculateTotalPaymentByMonth(Integer year, Integer month) {
        return admin.calculateTotalPaymentByMonth(year, month);
    }

    private Long countApartmentsByStatus(ApartmentStatus status) {
        return apartments.stream()
            .filter(apartment -> apartment.getStatus().equals(status))
            .count();
    }

    public Double getPercentageOfEmptyApartments() {
        Long habitedApartments = countApartmentsByStatus(ApartmentStatus.HABITED);
        return (double) habitedApartments / apartments.size() * 100.0;
    }

    public Double getPercentageOfRentedApartments() {
        Long rentedApartments = countApartmentsByStatus(ApartmentStatus.RENTED);
        return (double) rentedApartments / apartments.size() * 100.0;
    }

}