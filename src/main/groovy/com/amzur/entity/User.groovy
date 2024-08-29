package com.amzur.entity

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable

class User {
    Long id
   String name
    String email
    String password
    String phoneNumber
    String address

    User( Long id,String name, String address, String phoneNumber, String password, String email) {
        this.id=id
        this.name = name
        this.address = address
        this.phoneNumber = phoneNumber
        this.password = password
        this.email = email
    }

    @Override
     String toString() {
        return "User{ id=$id,name= $name , email=$email , password=$password ,phoneNumber= $phoneNumber ,address=$address }"
    }
}
