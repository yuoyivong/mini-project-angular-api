package com.example.library_management.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UploadFileImageResponse<T> {
    private String message;
    private Integer status;
    private T payload;
}
