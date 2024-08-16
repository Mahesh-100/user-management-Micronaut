package com.amzur.handlers

import io.micronaut.core.annotation.Introspected
import io.micronaut.serde.annotation.Serdeable

@Introspected
@Serdeable
class ErrorResponse {
    int status
    String error
    String message

    ErrorResponse(int status, String error, String message) {
        this.status = status
        this.error = error
        this.message = message
    }
}
