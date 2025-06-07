package response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public record ApiError(
        int status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime timestamp,
        String message,
        String debugMessage,
        List<ApiSubError> subErrors
) {

    // Custom constructor
    public ApiError(int status, String message, String debugMessage, List<ApiSubError> subErrors) {
        this(
                status,
                LocalDateTime.now(),
                message,
                debugMessage,
                subErrors == null ? Collections.emptyList() : Collections.unmodifiableList(subErrors)
        );
    }
}
