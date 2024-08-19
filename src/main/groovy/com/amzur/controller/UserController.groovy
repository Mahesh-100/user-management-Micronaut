package com.amzur.controller

import com.amzur.dto.request.UserRequest
import com.amzur.entity.User
import com.amzur.service.UserService
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import io.micronaut.http.annotation.Status
import jakarta.inject.Inject

import javax.validation.Valid


@Controller('/users')
class UserController {

    @Inject
    UserService userService

//    UserController(UserService userService) {
//        this.userService = userService
//    }

   @Post
   @Status(HttpStatus.CREATED)
    def addUser(@Valid @Body UserRequest userRequest){
        return "user ID: ${userService.addUser(userRequest)}"
    }
   @Delete("/{userId}")
    def removeUser(@PathVariable  int userId){
       return  userService.removeUser(userId)
    }
    @Get
    def getAllUsers(){
        return userService.getAllUsers()
    }


    @Put("/{userId}")
    def updateUser(@PathVariable int userId, @Body User user) {
        return userService.updateUser(userId, user)
    }
    @Get("/{userId}")
    def getEmail(@PathVariable int userId){
        return userService.getEmail(userId)
    }
}
