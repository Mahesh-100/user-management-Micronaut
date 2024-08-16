package com.amzur.handlers

class UserNotFound extends RuntimeException {

    UserNotFound(String msg) {
        super(msg)
    }
}
