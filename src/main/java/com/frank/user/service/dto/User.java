package com.frank.user.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(JsonInclude.Include.ALWAYS)
public class User {

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "FirstName is mandatory")
    private String firstName;

    @NotBlank(message = "LastName is mandatory")
    private String lastName;

    private String gender;
    private Address address;
}
