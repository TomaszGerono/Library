package com.tg.library.model;

// TODO klasa powinna byc oddzielnie ale na razie ją lacze
public record AddressDTO(Long id, String country, String region, String city, String street, String streetNumber,
                         String houseNumber) {
}
