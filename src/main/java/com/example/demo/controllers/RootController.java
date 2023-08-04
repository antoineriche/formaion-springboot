package com.example.demo.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/")
public class RootController {


    @Operation(
            summary = "Say hello to user",
            description = "Say hello to user passed as query param"
    )
    @GetMapping(
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public ResponseEntity<String> sayHello(@RequestParam(name = "name", required = false) String name) {
        log.debug("[GET] Say hello: {}", name);
        return ResponseEntity.ok("Hello %s!".formatted(Optional.ofNullable(name).orElse("")));
    }
}
