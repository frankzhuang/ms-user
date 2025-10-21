package com.frank.user.jpa;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class AddressDab implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "STREET")
    private String street;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "POST_CODE")
    private String postCode;

}
