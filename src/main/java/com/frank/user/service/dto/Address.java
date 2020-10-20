package com.frank.user.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class Address {
    private String street;
    private String city;
    private String state;
    private String postCode;

}
