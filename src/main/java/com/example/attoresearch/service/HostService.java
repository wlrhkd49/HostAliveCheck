package com.example.attoresearch.service;

import com.example.attoresearch.dto.ResponseDTO;
import com.example.attoresearch.entity.Host;
import com.example.attoresearch.repository.HostRepository;
import com.example.attoresearch.request.HostRequest;
import com.example.attoresearch.response.HostAliveResponse;
import com.example.attoresearch.response.HostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HostService {
    private HostRepository hostRepository;

    @Autowired
    public HostService(HostRepository hostRepository) {
        this.hostRepository = hostRepository;
    }

    // Create
    @Transactional
    public ResponseDTO<?> createHost(String name, String ip) {
        Optional<Host> opHost = hostRepository.findByNameAndIp(name, ip);
        if (opHost.isPresent()) {
            // 이미 존재하는 회원입니다.
            return ResponseDTO.ERROR("이미 존재하는 회원입니다.");
        }
        Host newHost = Host.builder()
                .name(name)
                .ip(ip)
                .build();

        hostRepository.save(newHost);

        return ResponseDTO.OK("name: "+ name + " ip: " + ip + "를 가진 호스트 생성 완료.");
    }

    // Read
    public ResponseDTO<?> getHosts() {
        List<Host> hosts = hostRepository.findAll();
        if(hosts.isEmpty()) {
            return ResponseDTO.ERROR("호스트가 존재하지 않습니다.");
        }
        List<HostResponse> hostResponses = new ArrayList<>();

        for (Host host : hosts) {
            hostResponses.add(
                    HostResponse.builder()
                            .name(host.getName())
                            .ip(host.getIp())
                            .lastAliveTime(host.getLastAliveTime())
                            .regDate(host.getRegDate())
                            .modDate(host.getModDate())
                            .build()
            );
        }
        return ResponseDTO.OK(hostResponses);
    }

    // Update
    @Transactional
    public ResponseDTO<?> updateHost(String name, String ip, HostRequest hostRequest) {
        Optional<Host> opHost = hostRepository.findByNameAndIp(name, ip);
        if (!opHost.isPresent()) {
            ResponseDTO.ERROR("해당 회원이 존재하지 않습니다.");
        }
        Optional<Host> checkHost = hostRepository.findByNameAndIp(hostRequest.getName(), hostRequest.getIp());
        if (checkHost.isPresent()) {
            ResponseDTO.ERROR("수정하고자하는 정보를 가진 유저가 이미 존재합니다.");
        }
        Host host = opHost.get();
        host.updateNameAndIp(hostRequest.getName(), hostRequest.getIp());

        return ResponseDTO.OK(
                HostResponse.builder()
                .name(host.getName())
                .ip(host.getIp())
                .lastAliveTime(host.getLastAliveTime())
                .regDate(host.getRegDate())
                .modDate(host.getModDate())
                .build()
        );
    }

    // Delete
    @Transactional
    public ResponseDTO<?> deleteHost(String name, String ip) {
        Optional<Host> opHost = hostRepository.findByNameAndIp(name, ip);
        if (!opHost.isPresent()) {
            // 해당 회원이 존재하지 않습니다.
            ResponseDTO.ERROR("해당 회원이 존재하지 않습니다.");
        }
        Host host = opHost.get();
        hostRepository.delete(host);
        return ResponseDTO.OK("name: " + host.getName() + " ip: " + host.getIp() + "호스트 계정을 삭제했습니다.");
    }

    @Transactional
    public ResponseDTO<?> getAliveHost(HostRequest hostRequest) {
        Optional<Host> opHost = hostRepository.findByNameAndIp(hostRequest.getName(), hostRequest.getIp());
        if (!opHost.isPresent()) {
            // 해당 회원이 존재하지 않습니다.
            return ResponseDTO.ERROR("해당 회원이 존재하지 않습니다.");
        }
        Host host = opHost.get();
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(hostRequest.getIp());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            if (inetAddress.isReachable(10)) {
                host.updateLastAliveTime(LocalDateTime.now());
                return ResponseDTO.OK(
                        HostAliveResponse.builder()
                                .name(host.getName())
                                .ip(host.getIp())
                                .lastAliveTime(host.getLastAliveTime())
                                .status("Reachable")
                                .build()
                );
            } else {
                return ResponseDTO.OK(
                        HostAliveResponse.builder()
                                .name(host.getName())
                                .ip(host.getIp())
                                .lastAliveTime(host.getLastAliveTime())
                                .status("UnReachable")
                                .build()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseDTO.ERROR(host.getIp() + "의 모니터링 결과를 찾을 수 없습니다.");
        }
    }

    @Transactional
    public ResponseDTO<?> getAliveHosts() {
        List<Host> hosts = hostRepository.findAll();
        List<HostAliveResponse> aliveResponses = new ArrayList<>();
        for(Host host : hosts) {
            HostAliveResponse hostAliveResponse = HostAliveResponse.builder()
                    .name(host.getName())
                    .ip(host.getIp())
                    .lastAliveTime(host.getLastAliveTime())
                    .build();
            InetAddress inetAddress = null;
            try {
                inetAddress = InetAddress.getByName(host.getIp());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            try {
                if (inetAddress.isReachable(10)) {
                    host.updateLastAliveTime(LocalDateTime.now());
                    hostAliveResponse.setStatus("Reachable");
                } else {
                    hostAliveResponse.setStatus("UnReachable");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                aliveResponses.add(hostAliveResponse);
            }
        }
        return ResponseDTO.OK(aliveResponses);
    }
}
