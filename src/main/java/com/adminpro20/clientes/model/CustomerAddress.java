package com.adminpro20.clientes.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class CustomerAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String number;
    private String suburb;
    private String code;
    private String phone;
    private String notes;
    private String city;
    private String state;
    private String country;
    private String email;

    @Override
    public String toString() {
        return "CustomerAdress{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", code='" + code + '\'' +
                ", phone='" + phone + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
