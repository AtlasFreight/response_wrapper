package response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ApiError {

    private final int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private final String message;
    private final String debugMessage;
    private final List<ApiSubError> subErrors;

    public ApiError(Builder builder) {
        this.status = builder.status;
        this.timestamp = LocalDateTime.now();
        this.message = Objects.requireNonNull(builder.message, "message must not be null");
        this.debugMessage = Objects.nonNull(builder.ex) ? builder.ex.getLocalizedMessage() : null;
        this.subErrors = Objects.isNull(builder.subErrors) ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<>(builder.subErrors));
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public List<ApiSubError> getSubErrors() {
        return subErrors;
    }

    public static class Builder {
        private int status;
        private String message;
        private Throwable ex;
        private List<ApiSubError> subErrors;

        public Builder setStatus(int status) {
            this.status = status;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setException(Throwable ex) {
            this.ex = ex;
            return this;
        }

        public Builder setSubErrors(List<ApiSubError> subErrors) {
            this.subErrors = subErrors;
            return this;
        }

        public ApiError build() {
            return new ApiError(this);
        }
    }
}