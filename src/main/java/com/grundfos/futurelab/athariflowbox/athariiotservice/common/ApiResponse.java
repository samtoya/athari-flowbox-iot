package com.grundfos.futurelab.athariflowbox.athariiotservice.common;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApiResponse<T> {
    private boolean isSuccess = true;
    private String message;
    private T data;
}
