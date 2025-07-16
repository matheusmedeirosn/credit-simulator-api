package com.creditsimulator.rest.exception;

import com.creditsimulator.domain.exception.SimulationNotFoundException;
import com.creditsimulator.rest.message.response.BaseResponse;
import com.creditsimulator.rest.message.response.exception.BadRequestResponse;
import com.creditsimulator.rest.message.response.exception.Inconsistency;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.creditsimulator.rest.utils.RestConstants.INTERNAL_ERROR_MESSAGE;
import static com.creditsimulator.rest.utils.RestConstants.MESSAGE_ERROR_VALIDATION;

@RestControllerAdvice
@Slf4j
public class HandlerAdvice {

    @SneakyThrows
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected BadRequestResponse handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        final List<Inconsistency> inconsistencyList = new ArrayList<>();
        Object target = exception.getBindingResult().getTarget();
        exception.getBindingResult().getAllErrors().forEach(error -> {
            final FieldError field = (FieldError) error;
            final String fieldName = findPtName(field, target).orElse(field.getField());
            final String message = field.getDefaultMessage();

            inconsistencyList.add(Inconsistency.builder().field(fieldName).description(message).build());
        });

        return BadRequestResponse.builder().inconsistencies(inconsistencyList).messageCode(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                .message(MESSAGE_ERROR_VALIDATION).build();
    }

    @ExceptionHandler(SimulationNotFoundException.class)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    protected void handleMethodArgumentNotValid(SimulationNotFoundException exception) {
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected BaseResponse handleGenericException(Exception exception) {
        log.error("Erro não mapeado: {}", exception.getMessage());

        return new BaseResponse(
                String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                INTERNAL_ERROR_MESSAGE
        );
    }

    public static Optional<String> findPtName(FieldError fieldError, Object targetObject) {
        try {
            Field field = targetObject.getClass().getDeclaredField(fieldError.getField());

            JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
            if (jsonProperty != null) {
                return Optional.of(jsonProperty.value());
            }

            return Optional.empty();
        } catch (NoSuchFieldException e) {
            return findInSuperclasses(targetObject.getClass(), fieldError.getField());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private static Optional<String> findInSuperclasses(Class<?> clazz, String fieldName) {
        Class<?> currentClass = clazz;
        while (currentClass != null && currentClass != Object.class) {
            try {
                Field field = currentClass.getDeclaredField(fieldName);
                JsonProperty jsonProperty = field.getAnnotation(JsonProperty.class);
                if (jsonProperty != null) {
                    return Optional.of(jsonProperty.value());
                }
            } catch (NoSuchFieldException e) {
            }
            currentClass = currentClass.getSuperclass();
        }
        return Optional.empty();
    }

}
