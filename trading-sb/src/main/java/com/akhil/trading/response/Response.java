package com.akhil.trading.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Response {
    @Builder.Default
    private String status="Success";
    @Builder.Default
    private LocalDateTime timeStamp= LocalDateTime.now();
    @Builder.Default
    private Object data=null;
    @Builder.Default
    private Object error=null;
}

