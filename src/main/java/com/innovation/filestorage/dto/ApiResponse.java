package com.innovation.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static com.innovation.filestorage.common.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private T content;

    private LocalDate timestamp;

    private String status;

    public static <T> ApiResponse<T> success(T content) {
        return new ApiResponse<>(
                content,
                LocalDate.now(),
                DEFAULT_API_RESPONSE_STATUS
        );
    }

}
