package com.amzur.service

import com.amzur.dto.request.UserRequest
import com.amzur.entity.User
import com.amzur.handlers.UserNotFound
import jakarta.inject.Singleton


@Singleton
class UserService {

    private Map<Integer, User> users = [:]
    private int nextUserId = 1


    def addUser(UserRequest userRequest){
        User user=new User(userRequest.name,userRequest.address,userRequest.phoneNumber,userRequest.password,userRequest.email)

        int userId=nextUserId++
        users[userId]=user
        return userId
    }
    def removeUser(int userId){
        if (users.containsKey(userId)) {   // Check if the userId exists
            users.remove(userId)           // Remove the user
            return true                    // Return true if user was removed
        }else{
            throw new UserNotFound("User Not Found")
        }

    }

    def getAllUsers(){
        return users
    }
    def updateUser(int userId, User updatedUser) {
        if (users.containsKey(userId)) {
            users[userId] = updatedUser
            return true
        }else{
            throw new UserNotFound("User Not Found")
        }

    }

}
