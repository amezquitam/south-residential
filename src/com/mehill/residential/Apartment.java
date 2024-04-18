package com.mehill.residential;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Apartment {
    private String id;
    private Integer floor;
    private Owner owner;
    private ApartmentStatus status;

    public Apartment() {
    }

    public Apartment(String id, Integer floor, Owner owner, ApartmentStatus status) {
        setId(id);
        setFloor(floor);
        this.owner = owner;
        this.status = status;
    }

    public Integer getFloor() {
        return floor;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public ApartmentStatus getStatus() {
        return status;
    }

    public void setStatus(ApartmentStatus status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        Integer idLength = id.length();

        if (idLength != 3 && idLength != 4) {
            throw new IllegalArgumentException("El ID del apartamento debe tener 3 o 4 digitos");
        }

        Pattern apartmentIdPattern = Pattern.compile("^[0-9]{3,4}$");
        Matcher matcher = apartmentIdPattern.matcher(id);

        if (!matcher.find()) {
            throw new IllegalArgumentException("El ID del apartamento solo debe contener números");
        }
        this.id = id;
    }

    public void setFloor(Integer floor) {
        if (floor < 1 || floor > 10) {
            throw new IllegalArgumentException("El número del piso debe estar entre 1 y 10");
        }
        
        this.floor = floor;
    }
}
