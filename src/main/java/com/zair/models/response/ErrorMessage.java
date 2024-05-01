package com.zair.models.response;

import java.util.Date;

public record ErrorMessage(Integer statusCode, String description, String message, Date timestamp) {
}
