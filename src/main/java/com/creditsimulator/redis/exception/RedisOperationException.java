package com.creditsimulator.redis.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RedisOperationException extends RuntimeException{

    private final String operation;
    private final String key;
    private final String errorType;

    public RedisOperationException(String message, Throwable cause) {
        this(null, null, null, message, cause);
    }

    private RedisOperationException(String operation, String key, String errorType, String message, Throwable cause) {
        this.operation = operation;
        this.key = key;
        this.errorType = errorType;
    }

}
