package com.kynsoft.auth.controller;

import com.kynsoft.auth.dto.AssignRoleRequest;
import com.kynsoft.auth.dto.ChangeStatusRequest;
import com.kynsoft.auth.dto.CreateUserRequest;
import com.kynsoft.auth.dto.UpdateUserRequest;
import com.kynsoft.auth.dto.UserResponse;
import com.kynsoft.auth.enums.UserStatus;
import com.kynsoft.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "User Management", description = "CRUD operations for users")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @GetMapping
    @Operation(summary = "List users", description = "Paginated user list with optional status filter")
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    public ResponseEntity<Page<UserResponse>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) UserStatus status) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(userService.findAll(pageable, status));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    @ApiResponse(responseCode = "200", description = "User found")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Create user")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "409", description = "Email already exists")
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        log.info("Create user request for email: {}", request.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.createUser(request, httpServletRequest));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update user")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<UserResponse> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequest request) {
        log.info("Update user request for id: {}", id);
        return ResponseEntity.ok(userService.updateUser(id, request, httpServletRequest));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Change user status")
    @ApiResponse(responseCode = "204", description = "Status changed successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public ResponseEntity<Void> changeStatus(
            @PathVariable UUID id,
            @Valid @RequestBody ChangeStatusRequest request) {
        userService.changeStatus(id, request, httpServletRequest);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/roles")
    @Operation(summary = "Assign role to user")
    @ApiResponse(responseCode = "204", description = "Role assigned successfully")
    @ApiResponse(responseCode = "404", description = "User or role not found")
    public ResponseEntity<Void> assignRole(
            @PathVariable UUID id,
            @Valid @RequestBody AssignRoleRequest request) {
        userService.assignRole(id, request.getRoleName(), httpServletRequest);
        return ResponseEntity.noContent().build();
    }
}
