package com.example.attoresearch.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class HostResponse {
    private String name;
    private String ip;
    private LocalDateTime lastAliveTime;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
