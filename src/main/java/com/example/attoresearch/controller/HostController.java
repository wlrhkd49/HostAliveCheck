package com.example.attoresearch.controller;

import com.example.attoresearch.dto.Criteria;
import com.example.attoresearch.dto.ResponseDTO;
import com.example.attoresearch.request.HostRequest;
import com.example.attoresearch.response.HostAliveResponse;
import com.example.attoresearch.service.HostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/host")
public class HostController {

    private HostService hostService;

    @Autowired
    public HostController(HostService hostService) {
        this.hostService = hostService;
    }

    //Create
    // 호스트 등록 시 필드는 name, ip 이다.
    @PostMapping
    public ResponseDTO<?> createHost(@RequestParam String name, @RequestParam String ip) {
        return hostService.createHost(name, ip);
    }

    //Read
    @GetMapping
    public ResponseDTO<?> getHosts() {
        return hostService.getHosts();
    }

    //Update
    @PutMapping
    public ResponseDTO<?> updateHost(@RequestParam String name, @RequestParam String ip, @RequestBody HostRequest hostRequest) {
        return hostService.updateHost(name, ip, hostRequest);
    }

    //Delete
    @DeleteMapping
    public ResponseDTO<?> deleteHost(@RequestParam String name, @RequestParam String ip) {
        return hostService.deleteHost(name, ip);
    }

    // 특정 호스트의 현재 Alive 상태 조회 REST API
    // 조회의 단위는 한 호스트만 가능해야 한다.
    // Alive 상태 확인은 InetAddress.isReachable() 사용 권장
    @GetMapping("/alive")
    public ResponseDTO<?> getAliveHost(@RequestBody HostRequest hostRequest) {
        return hostService.getAliveHost(hostRequest);
    }

    // 호스트들의 Alive 모니터링 결과 조회 REST API
    // list
    @GetMapping("/alive/list")
    public ResponseDTO<?> getAliveHosts(@RequestParam Criteria criteria,
                                        @RequestBody(required = false) HostRequest hostRequest) {
        // 한 호스트 조회일 경우 (제약조건, ResponseBody 필수)
        if(criteria == Criteria.ONE) {
            return hostService.getAliveHost(hostRequest);
        }
        // 모든 호스트 조회일 경우
        else {
            return hostService.getAliveHosts();
        }
    }

}
