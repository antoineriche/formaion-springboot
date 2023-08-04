package com.example.demo.controllers;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/users")
public class UserController {

    @Autowired // Annotation to tell Spring that we need an instance of UserService to be injected
    private UserService userService;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Find all users",
            description = "Find all users"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content = @Content(schema = @Schema(implementation = String.class))
    )
    public ResponseEntity<List<User>> fetchAllUsers() {
        log.debug("[GET] Fetch all users");
        final List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(
            path = "/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Find user by id",
            description = "Find a user by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content = @Content(schema = @Schema(implementation = User.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not found",
            content = @Content(schema = @Schema(implementation = Void.class))
    )
    public ResponseEntity<User> fetchUserById(@PathVariable(name = "userId") final Long id) {
        log.debug("[GET] Fetch user by id: {}", id);
        final Optional<User> optUser = userService.findUserById(id);
        return optUser
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Save user",
            description = "Create a new user"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content = @Content(schema = @Schema(implementation = User.class))
    )
    public ResponseEntity<User> saveUser(@RequestBody final User user) {
        log.debug("[POST] Save user: {}", user.getLogin());
        final User neoUser = userService.saveUser(user);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(neoUser.getId())
                        .toUri())
                .body(neoUser);
    }

    @DeleteMapping(
            path = "/{userId}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(
            summary = "Delete user",
            description = "Delete an user by id"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Ok",
            content = @Content(schema = @Schema(implementation = User.class))
    )
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = @Content(schema = @Schema(implementation = User.class))
    )
    public ResponseEntity<Void> deleteUser(@PathVariable(name = "userId") final Long id) {
        log.debug("[POST] Delete user: {}", id);
        final boolean deleted = userService.deleteUserById(id);
        return deleted ? ResponseEntity.accepted().build()
                : ResponseEntity.notFound().build();
    }
}
