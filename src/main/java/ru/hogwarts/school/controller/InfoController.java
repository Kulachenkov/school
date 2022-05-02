package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

@RestController
@RequestMapping("getPort")
public class InfoController {
    @Value("${server.port}")
    private Integer port;

    Logger logger = LoggerFactory.getLogger(InfoController.class);

    @GetMapping
    public ResponseEntity<Integer> getPort() {
        return ResponseEntity.ok(port);
    }

    @GetMapping("/stream")
    public ResponseEntity<Integer> getValue() {
        long start = System.currentTimeMillis();
        int sum = IntStream.rangeClosed(1, 1000000).parallel().reduce(0, Integer::sum);
        logger.info((System.currentTimeMillis() - start) + " ms");
        return ResponseEntity.ok(sum);
    }
}
