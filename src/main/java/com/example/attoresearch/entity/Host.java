package com.example.attoresearch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Table(
        name="host",
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"name", "ip"}
                )
        }
)
public class Host{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String ip;

    private LocalDateTime lastAliveTime;

    @CreatedDate
    private LocalDateTime regDate;
    @LastModifiedDate
    private LocalDateTime modDate;

    @Builder
    public Host(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public void updateNameAndIp(String name, String ip) {
        this.name = name;
        this.ip = ip;
    }

    public void updateLastAliveTime(LocalDateTime lastAliveTime) {
        this.lastAliveTime = lastAliveTime;
    }
}
