package com.amzur.service

import com.amzur.entity.User
import jakarta.inject.Singleton


@Singleton
class UserService {

    private Map<Integer, User> users = [:]
    private int nextUserId = 1


    def addUser(User user){
        int userId=nextUserId++
        users[userId]=user
        return userId
    }
    def removeUser(int userId){
        if (users.containsKey(userId)) {   // Check if the userId exists
            users.remove(userId)           // Remove the user
            return true                    // Return true if user was removed
        }
        return false
    }

    def getAllUsers(){
        return users
    }
    def updateUser(int userId, User updatedUser) {
        if (users.containsKey(userId)) {
            users[userId] = updatedUser
            return true
        }
        return false
    }

}
