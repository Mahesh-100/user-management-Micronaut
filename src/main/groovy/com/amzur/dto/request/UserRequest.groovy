package com.amzur.dto.request

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Introspected
@Serdeable
class UserRequest {
    @NotNull(message = "Please provide Name")
    @NotEmpty(message = "Name should not be empty")
    String name

    @NotNull(message = "Please provide Phone Number")
    @NotEmpty(message = "Phone Number should not be empty")
    String phoneNumber

    @NotNull(message = "Please provide Email")
    @NotEmpty(message = "Email should not be empty")
    String email

    @NotNull(message = "Please provide Password")
    @NotEmpty(message = "Password should not be empty")
    String password

    @NotNull(message = "Please provide Address")
    @NotEmpty(message = "Address should not be empty")
    String address


    @Override
     String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
