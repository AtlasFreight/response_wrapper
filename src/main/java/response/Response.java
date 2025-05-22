package response;

import java.util.Collection;
import java.util.List;

public class Response<T> {
    private final boolean success;
    private final ApiError apiError;
    private final T data;

    private Response(boolean success, ApiError apiError, T data) {
        this.success = success;
        this.apiError = apiError;
        this.data = (data instanceof Collection) ? (T) List.copyOf((Collection<?>) data) : data;
    }

    public static <T> Response<T> success(T data) {
        return new Response<>(true, null, data);
    }

    public static <T> Response<T> fail(ApiError apiError) {
        return new Response<>(false, apiError, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public ApiError getApiError() {
        return apiError;
    }

    public T getData() {
        return data;
    }
}