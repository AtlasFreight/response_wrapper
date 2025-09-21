package com.atlasfreight.response;

import java.util.Optional;
import com.atlasfreight.response.error.ApiError;

/**
 * Defines a contract for standardized API responses in a microservices architecture.
 *
 * <p>The {@code ResponsePattern} interface provides a consistent structure for
 * handling both successful and failed operations, enabling better error handling
 * and response processing across services.</p>
 *
 * @param <T> the type of data contained in a successful response
 *
 * @author Eduardo Díaz
 * @version 1.0
 */
public interface ResponsePattern<T> {

    /**
     * Indicates whether the operation was successful.
     *
     * @return {@code true} if the operation completed successfully, {@code false} otherwise
     */
    boolean isSuccess();

    /**
     * Returns the error details if the operation failed.
     *
     * <p>This method returns an {@link Optional} that will be empty for successful responses
     * and contain an {@link ApiError} for failed operations.</p>
     *
     * @return an {@link Optional} containing error details if available, empty otherwise
     */
    Optional<ApiError> getError();

    /**
     * Returns the response data if the operation was successful.
     *
     * <p>This method returns an {@link Optional} that will contain the response data
     * for successful operations and be empty for failed ones.</p>
     *
     * @return an {@link Optional} containing the response data if available, empty otherwise
     */
    Optional<T> getData();
}