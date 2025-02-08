package net.mediger.global.exception.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.mediger.global.exception.ErrorCode;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {

    private HttpStatus status;
    private int code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(HttpStatus.OK, HttpStatus.OK.value(), null, data);
    }

    public static ApiResponse<Void> successWithNoContent() {
        return new ApiResponse<>(HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value(), null, null);
    }

    public static <T> ApiResponse<T> error(ErrorCode errorCode) {
        return new ApiResponse<>(errorCode.getStatus(), errorCode.getStatus().value(), errorCode.getMessage(), null);
    }
}
