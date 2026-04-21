package com.kynsoft.auth.service;

import com.kynsoft.auth.dto.AssignRoleRequest;
import com.kynsoft.auth.dto.ChangeStatusRequest;
import com.kynsoft.auth.dto.CreateUserRequest;
import com.kynsoft.auth.dto.UpdateUserRequest;
import com.kynsoft.auth.dto.UserResponse;
import com.kynsoft.auth.enums.RoleName;
import com.kynsoft.auth.enums.UserStatus;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserService {
    UserResponse createUser(CreateUserRequest request, HttpServletRequest httpRequest);
    UserResponse updateUser(UUID id, UpdateUserRequest request, HttpServletRequest httpRequest);
    void changeStatus(UUID id, ChangeStatusRequest request, HttpServletRequest httpRequest);
    UserResponse findById(UUID id);
    Page<UserResponse> findAll(Pageable pageable, UserStatus statusFilter);
    void assignRole(UUID userId, RoleName roleName, HttpServletRequest httpRequest);
}
