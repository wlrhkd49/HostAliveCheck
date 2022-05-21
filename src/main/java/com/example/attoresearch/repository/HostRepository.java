package com.example.attoresearch.repository;

import com.example.attoresearch.entity.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HostRepository extends JpaRepository<Host, Long> {
    Optional<Host> findByNameAndIp(String name, String ip);
}
