package response;

import java.util.Optional;

/**
 * Standardized response container for API operations in a microservices architecture.
 *
 * <p>This class implements the Result Pattern to provide consistent handling of both
 * successful and failed operations across services. It ensures type-safe response
 * handling with proper error information and HTTP status codes.</p>
 *
 * @param <T> the type of data contained in a successful response
 *
 * @author Eduardo Díaz
 * @version 1.0
 * @see ResponsePattern
 * @see ApiError
 */
public class Response<T> implements ResponsePattern<T> {
    private final boolean success;
    private final ApiError apiError;
    private final T data;
    private final int statusCode;

    /**
     * Constructs a new Response instance.
     *
     * @param success indicates whether the operation was successful
     * @param apiError the error details if the operation failed, null otherwise
     * @param data the response data if the operation was successful, null otherwise
     * @param statusCode the HTTP status code associated with the response
     */
    private Response(boolean success, ApiError apiError, T data, int statusCode) {
        this.success = success;
        this.apiError = apiError;
        this.data = data;
        this.statusCode = statusCode;
    }

    /**
     * Creates a successful response with default 200 status code.
     *
     * @param <T> the type of the response data
     * @param data the data to include in the successful response
     * @return a successful Response containing the provided data
     * @throws IllegalArgumentException if data is null
     */
    public static <T> Response<T> success(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null for successful responses");
        }
        return new Response<>(true, null, data, 200);
    }

    /**
     * Creates a successful response with a custom status code.
     *
     * @param <T> the type of the response data
     * @param data the data to include in the successful response
     * @param statusCode the custom HTTP status code (typically 2xx)
     * @return a successful Response containing the provided data and status code
     * @throws IllegalArgumentException if data is null or statusCode is not a success code
     */
    public static <T> Response<T> success(T data, int statusCode) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null for successful responses");
        }
        if (statusCode < 200 || statusCode >= 300) {
            throw new IllegalArgumentException("Status code for successful responses must be in the 2xx range");
        }
        return new Response<>(true, null, data, statusCode);
    }

    /**
     * Creates a failed response based on an ApiError object.
     *
     * @param <T> the type of the response data
     * @param apiError the error details containing status and message
     * @return a failed Response with the specified error information
     * @throws IllegalArgumentException if apiError is null
     */
    public static <T> Response<T> fail(ApiError apiError) {
        if (apiError == null) {
            throw new IllegalArgumentException("ApiError cannot be null for failed responses");
        }
        return new Response<>(false, apiError, null, apiError.status());
    }

    /**
     * Creates a failed response with a simple error message.
     *
     * @param <T> the type of the response data
     * @param statusCode the HTTP status code for the error (typically 4xx or 5xx)
     * @param message the user-friendly error message
     * @return a failed Response with the specified error details
     */
    public static <T> Response<T> fail(int statusCode, String message) {
        ApiError error = new ApiError(statusCode, message);
        return new Response<>(false, error, null, statusCode);
    }

    /**
     * Indicates whether the operation was successful.
     *
     * @return true if the operation completed successfully, false otherwise
     */
    @Override
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the error details if the operation failed.
     *
     * @return an Optional containing error details if available, empty otherwise
     */
    @Override
    public Optional<ApiError> getError() {
        return Optional.ofNullable(apiError);
    }

    /**
     * Returns the response data if the operation was successful.
     *
     * @return an Optional containing the response data if available, empty otherwise
     */
    @Override
    public Optional<T> getData() {
        return Optional.ofNullable(data);
    }

    /**
     * Returns the HTTP status code associated with the response.
     *
     * @return the HTTP status code representing the response outcome
     */
    @Override
    public int getStatusCode() {
        return statusCode;
    }

    /**
     * Transforms the response data if the operation was successful.
     *
     * @param <U> the type of the transformed data
     * @param mapper the function to apply to the data
     * @return a new Response with transformed data if successful, or the original error if failed
     */
    public <U> Response<U> map(java.util.function.Function<T, U> mapper) {
        if (!isSuccess()) {
            return Response.fail(this.apiError);
        }
        return Response.success(mapper.apply(data), this.statusCode);
    }

    /**
     * Executes the specified action if the operation was successful.
     *
     * @param consumer the action to execute with the response data
     * @return this Response for method chaining
     */
    public Response<T> ifSuccess(java.util.function.Consumer<T> consumer) {
        if (isSuccess()) {
            consumer.accept(data);
        }
        return this;
    }

    /**
     * Executes the specified action if the operation failed.
     *
     * @param consumer the action to execute with the error details
     * @return this Response for method chaining
     */
    public Response<T> ifFailure(java.util.function.Consumer<ApiError> consumer) {
        if (!isSuccess()) {
            consumer.accept(apiError);
        }
        return this;
    }

    @Override
    public String toString() {
        return "Response{" +
                "success=" + success +
                ", statusCode=" + statusCode +
                ", data=" + data +
                ", apiError=" + apiError +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;

        // Verificación usando instanceof en lugar de getClass()
        if (!(o instanceof ResponsePattern)) return false;

        ResponsePattern<?> that = (ResponsePattern<?>) o;

        // Comparación basada en el estado, no en la clase exacta
        if (success != that.isSuccess()) return false;
        if (statusCode != that.getStatusCode()) return false;

        // Comparación de datos
        if (!java.util.Objects.equals(getData().orElse(null), that.getData().orElse(null))) return false;

        // Comparación de errores
        return java.util.Objects.equals(getError().orElse(null), that.getError().orElse(null));
    }

    @Override
    public int hashCode() {
        int result = (success ? 1 : 0);
        result = 31 * result + (apiError != null ? apiError.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        result = 31 * result + statusCode;
        return result;
    }
}