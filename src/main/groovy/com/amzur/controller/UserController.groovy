package com.amzur.controller

import com.amzur.entity.User
import com.amzur.service.UserService
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.http.annotation.QueryValue
import jakarta.inject.Inject


@Controller('/users')
class UserController {

    @Inject
    UserService userService

//    UserController(UserService userService) {
//        this.userService = userService
//    }

   @Post
    def addUser(@Body User user){
        return "user ID: ${userService.addUser(user)}"
    }
   @Delete
    def removeUser(@QueryValue  int userId){
        if(userService.removeUser(userId)){
            return "successfully Removed user"
        }else{
            return "User not Found"
        }
    }
    @Get
    def getAllUsers(){
        return userService.getAllUsers()
    }

    @Put("/{userId}")
    def updateUser(@PathVariable int userId, @Body User user) {
        if(userService.updateUser(userId, user)){
            return "Successfully updated"

        } else{
            return  "User not Found"
        }
    }
}
