package com.thihahtetkyaw.booking.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GeneralDto<T> {

    public static final long SUCCESS_CODE = 0L;
    public static final long PRESENTATION_LAYER_ERROR_CODE = 100L;
    public static final long BUSINESS_LAYER_ERROR_CODE = 200L;
    public static final long DATA_ACCESS_LAYER_ERROR_CODE = 300L;
    public static final long INTEGRATION_LAYER_ERROR_CODE = 400L;
    public static final long GENERAL_ERROR_CODE = 500L;
    public static final long AUTHENTICATION_FAIL_ERROR_CODE = 600L;
    public static final long AUTHORIZATION_FAIL_ERROR_CODE = 700L;

    private long statusCode;
    private String message;
    private String title;
    private T data;

    public static <T> GeneralDto<T> withSuccess(String message, T data) {
        return GeneralDto.<T>builder().statusCode(SUCCESS_CODE).message(message).data(data).build();
    }

    public static <T> GeneralDto<T> withSuccess(String title, String message, T data) {
        return GeneralDto.<T>builder().title(title).statusCode(SUCCESS_CODE).message(message).data(data).build();
    }

    public static GeneralDto<?> withSuccess(String message) {
        return withSuccess(message, null);
    }

    public static GeneralDto<?> withAuthenticationFail(String title, String message) {
        return GeneralDto.builder().statusCode(AUTHENTICATION_FAIL_ERROR_CODE).title(title).message(message).build();
    }

    public static GeneralDto<?> withAuthorizationFail(String title, String message) {
        return GeneralDto.builder().statusCode(AUTHORIZATION_FAIL_ERROR_CODE).title(title).message(message).build();
    }

    public static GeneralDto<?> withGeneralError(String title, String message) {
        return GeneralDto.builder().statusCode(GENERAL_ERROR_CODE).title(title).message(message).build();
    }

    public static GeneralDto<?> withBusinessError(String title, String message) {
        return GeneralDto.builder().statusCode(BUSINESS_LAYER_ERROR_CODE).title(title).message(message).build();
    }

    public static GeneralDto<?> withIntegrationError(String title, String message) {
        return GeneralDto.builder().statusCode(INTEGRATION_LAYER_ERROR_CODE).title(title).message(message).build();
    }

    public static GeneralDto<?> withDataLayerError(String title, String message) {
        return GeneralDto.builder().statusCode(DATA_ACCESS_LAYER_ERROR_CODE).title(title).message(message).build();
    }

    public static GeneralDto<?> withPresentationError(String title, String message) {
        return GeneralDto.builder().statusCode(PRESENTATION_LAYER_ERROR_CODE).title(title).message(message).build();
    }


}
