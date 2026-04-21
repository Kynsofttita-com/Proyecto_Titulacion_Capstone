package com.kynsoft.auth.service;

import com.kynsoft.auth.dto.ChangeStatusRequest;
import com.kynsoft.auth.dto.CreateUserRequest;
import com.kynsoft.auth.dto.UpdateUserRequest;
import com.kynsoft.auth.dto.UserResponse;
import com.kynsoft.auth.entity.AuditLog;
import com.kynsoft.auth.entity.Role;
import com.kynsoft.auth.entity.User;
import com.kynsoft.auth.enums.AuditAction;
import com.kynsoft.auth.enums.RoleName;
import com.kynsoft.auth.enums.UserStatus;
import com.kynsoft.auth.exception.DuplicateEmailException;
import com.kynsoft.auth.exception.UserNotFoundException;
import com.kynsoft.auth.repository.AuditLogRepository;
import com.kynsoft.auth.repository.RoleRepository;
import com.kynsoft.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuditLogRepository auditLogRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResponse createUser(CreateUserRequest request, HttpServletRequest httpRequest) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + request.getEmail());
        }

        Role role = roleRepository.findByName(request.getRoleName())
                .orElseThrow(() -> new RuntimeException("Role not found: " + request.getRoleName()));

        User user = User.builder()
                .email(request.getEmail())
                .passwordHash(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .status(UserStatus.ACTIVE)
                .roles(new HashSet<>(Set.of(role)))
                .build();

        user = userRepository.save(user);

        Map<String, Object> details = new HashMap<>();
        details.put("email", user.getEmail());
        details.put("role", request.getRoleName().toString());
        auditLogRepository.save(AuditLog.builder()
                .user(user)
                .action(AuditAction.USER_CREATED)
                .ipAddress(getClientIp(httpRequest))
                .userAgent(httpRequest.getHeader("User-Agent"))
                .details(details)
                .build());

        log.info("User created: {}", user.getEmail());
        return buildUserResponse(user);
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UpdateUserRequest request, HttpServletRequest httpRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user = userRepository.save(user);

        Map<String, Object> details = new HashMap<>();
        details.put("updatedFields", List.of("firstName", "lastName", "email"));
        auditLogRepository.save(AuditLog.builder()
                .user(user)
                .action(AuditAction.USER_UPDATED)
                .ipAddress(getClientIp(httpRequest))
                .userAgent(httpRequest.getHeader("User-Agent"))
                .details(details)
                .build());

        log.info("User updated: {}", user.getEmail());
        return buildUserResponse(user);
    }

    @Override
    @Transactional
    public void changeStatus(UUID id, ChangeStatusRequest request, HttpServletRequest httpRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));

        UserStatus previous = user.getStatus();
        user.setStatus(request.getStatus());
        userRepository.save(user);

        Map<String, Object> details = new HashMap<>();
        details.put("previousStatus", previous.toString());
        details.put("newStatus", request.getStatus().toString());
        auditLogRepository.save(AuditLog.builder()
                .user(user)
                .action(AuditAction.USER_STATUS_CHANGED)
                .ipAddress(getClientIp(httpRequest))
                .userAgent(httpRequest.getHeader("User-Agent"))
                .details(details)
                .build());

        log.info("User status changed: {} -> {}", previous, request.getStatus());
    }

    @Override
    public UserResponse findById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
        return buildUserResponse(user);
    }

    @Override
    public Page<UserResponse> findAll(Pageable pageable, UserStatus statusFilter) {
        if (statusFilter != null) {
            return userRepository.findAll(
                    (root, query, cb) -> cb.equal(root.get("status"), statusFilter),
                    pageable
            ).map(this::buildUserResponse);
        }
        return userRepository.findAll(pageable).map(this::buildUserResponse);
    }

    @Override
    @Transactional
    public void assignRole(UUID userId, RoleName roleName, HttpServletRequest httpRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));

        user.getRoles().add(role);
        userRepository.save(user);

        Map<String, Object> details = new HashMap<>();
        details.put("assignedRole", roleName.toString());
        auditLogRepository.save(AuditLog.builder()
                .user(user)
                .action(AuditAction.ROLE_ASSIGNED)
                .ipAddress(getClientIp(httpRequest))
                .userAgent(httpRequest.getHeader("User-Agent"))
                .details(details)
                .build());

        log.info("Role {} assigned to user {}", roleName, userId);
    }

    private UserResponse buildUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .status(user.getStatus().toString())
                .roles(user.getRoles().stream()
                        .map(role -> role.getName().toString())
                        .collect(Collectors.toList()))
                .lastLogin(user.getLastLogin())
                .createdAt(user.getCreatedAt())
                .build();
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
