package com.example.attoresearch.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HostAliveResponse {
    private String name;
    private String ip;
    private LocalDateTime lastAliveTime;
    private String status;
}
