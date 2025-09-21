package com.atlasfreight.response;

import com.atlasfreight.response.error.ApiError;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Standardized response container for API operations in a microservices architecture.
 *
 * <p>This record implements the Result Pattern to provide consistent handling of both
 * successful and failed operations across services. It ensures type-safe response
 * handling with proper error information.</p>
 *
 * @param <T> the type of data contained in a successful response
 * @param success indicates whether the operation was successful
 * @param apiError the error details if the operation failed, null otherwise
 * @param data the response data if the operation was successful, null otherwise
 *
 * @author Eduardo Díaz
 * @version 1.0
 * @see ResponsePattern
 * @see ApiError
 */
public record Response<T>(
        boolean success,
        ApiError apiError,
        T data
) implements ResponsePattern<T> {

    /**
     * Compact constructor for validation.
     */
    public Response {
        if (!success && Objects.isNull(apiError)) {
            throw new IllegalArgumentException("ApiError cannot be null for failed responses");
        }

        if (success && Objects.nonNull(apiError)) {
            throw new IllegalArgumentException("Successful responses cannot have an ApiError");
        }

        if (!success && Objects.nonNull(data)) {
            throw new IllegalArgumentException("Failed responses cannot have data");
        }
    }

    /**
     * Factory class for creating standardized {@link Response} instances.
     * Provides fluent methods for building both successful and failed responses.
     */
    public static final class Builder {

        private Builder() {
            // Utility class - prevent instantiation
        }

        /**
         * Creates a successful response containing the specified data.
         *
         * @param <T> the type of the response data
         * @param data the data to include in the successful response
         * @return a successful {@link Response} containing the provided data
         * @throws IllegalArgumentException if {@code data} is {@code null}
         */
        public static <T> Response<T> success(T data) {
            return new Response<>(true, null, data);
        }

        /**
         * Creates a failed response based on a pre-constructed {@link ApiError} object.
         *
         * @param <T> the type of the response data
         * @param error the {@link ApiError} containing error details
         * @return a failed {@link Response} with the specified error information
         * @throws IllegalArgumentException if {@code error} is {@code null}
         */
        public static <T> Response<T> failure(ApiError error) {
            return new Response<>(false, error, null);
        }
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public Optional<ApiError> getError() {
        // Because Optional.ofNullable(null) == Optional.empty();
        return Optional.ofNullable(apiError);
    }

    @Override
    public Optional<T> getData() {
        return Optional.ofNullable(data);
    }

    public <U> Response<U> map(Function<T, U> mapper) {
        if (!success) {
            return Response.Builder.failure(apiError);
        }
        return Response.Builder.success(mapper.apply(data));
    }

    public Response<T> ifSuccess(Consumer<T> consumer) {
        if (success) {
            consumer.accept(data);
        }
        return this;
    }

    public Response<T> ifFailure(Consumer<ApiError> consumer) {
        if (!success) {
            consumer.accept(apiError);
        }
        return this;
    }
}