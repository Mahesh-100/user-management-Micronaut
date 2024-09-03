package com.amzur.controller

import com.amzur.dto.request.UserRequest
import com.amzur.entity.User
import com.amzur.service.MessageProducer
import com.amzur.service.UserService
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Options
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put

import io.micronaut.http.annotation.Status

import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.scheduling.annotation.ExecuteOn
import io.micronaut.scheduling.TaskExecutors
import jakarta.inject.Inject


@Controller('/users')
class UserController {

    @Inject
    UserService userService
    @Inject
    MessageProducer messageProducer


    @Inject
    @Client("http://localhost:8083") // URL of the second microservice
    HttpClient httpClient



    @ExecuteOn(TaskExecutors.BLOCKING)
    @Post
    @Status(HttpStatus.CREATED)
    def createUser(@Body UserRequest userRequest) {
        try {
            // Send the UserRequest to the other microservice
            HttpResponse<User> response = httpClient.toBlocking().exchange(
                    HttpRequest.POST("/user-process", userRequest),
                    User
            )

            // Check if the request was successful
            return handleResponse(response)
        } catch (Exception e) {
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }


    @ExecuteOn(TaskExecutors.BLOCKING)
    @Put("/update/{userId}")
    @Status(HttpStatus.CREATED)
    def updateUser(@PathVariable int userId, @Body UserRequest userRequest) {
        try {
            // Send the UserRequest to the other microservice
            HttpResponse<User> response = httpClient.toBlocking().exchange(
                    HttpRequest.PUT("/user-process/update/${userId}", userRequest),
                    User
            )

            // Check if the request was successful
            return handleResponse(response)
        } catch (Exception e) {
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }

    private def handleResponse(HttpResponse<User> response) {
        if (response.status == HttpStatus.CREATED && response.body()) {
            return messageProducer.sendMessage(response.body()) ?
                    HttpResponse.ok(response.body()) :
                    HttpResponse.serverError("Unable to send user object through Kafka")
        }
        return HttpResponse.status(response.status).body("Failed to process user request in the other microservice")
    }
    @ExecuteOn(TaskExecutors.BLOCKING)
    @Post("/login")
    @Status(HttpStatus.OK)
    def loginUser(@Body UserRequest userRequest) {
        try {
            // Send the UserRequest to the other microservice
            HttpResponse<User> response = httpClient.toBlocking().exchange(
                    HttpRequest.POST("/user-process/login", userRequest),
                    User
            )
      println (response.body())
            // Check if the request was successful
            if (response.status == HttpStatus.OK  && response.body()) {
                // Send the response user object through Kafka
                if (messageProducer.sendMessage(response.body())) {
                    return HttpResponse.ok(response.body())
                } else {
                    return HttpResponse.serverError("Unable to send user object through Kafka")
                }
            } else {
                return HttpResponse.status(response.status).body("Failed to process user request in the other microservice")
            }
        } catch (Exception e) {
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }

    @ExecuteOn(TaskExecutors.BLOCKING)
    @Delete("/delete/{userId}")
    @Status(HttpStatus.NO_CONTENT)
    def removeUser(@PathVariable int userId) {
        try{
        HttpResponse<Void> response = httpClient.toBlocking().exchange(
                HttpRequest.DELETE("/user-process/delete/${userId}")
        )

        if (response.status == HttpStatus.NO_CONTENT) {
            return HttpResponse.noContent() // Return 204 No Content for successful DELETE
        }else {
            return HttpResponse.status(response.status).body("Failed to process user request in the other microservice")
        }
    }catch(Exception e){
            return HttpResponse.serverError("An error occurred: ${e.message}")
        }
    }



//    @Delete("/{userId}")
//    def removeUser(@PathVariable  int userId){
//       return  userService.removeUser(userId)
//    }
//    @Get
//    def getAllUsers(){
//        return userService.getAllUsers()
//    }
//
//

//    @Get("/{userId}")
//    def getEmail(@PathVariable int userId){
//        return userService.getEmail(userId)
//    }
}
