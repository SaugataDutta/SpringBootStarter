package com.tgt.mkt.cam.domain;

import lombok.*;
import lombok.experimental.Builder;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String errorMessage;
    private Map<String, String> data;

}
