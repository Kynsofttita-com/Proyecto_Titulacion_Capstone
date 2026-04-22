package com.kynsoft.auth.service;

import com.kynsoft.auth.dto.ChangeStatusRequest;
import com.kynsoft.auth.dto.CreateUserRequest;
import com.kynsoft.auth.dto.UpdateUserRequest;
import com.kynsoft.auth.dto.UserResponse;
import com.kynsoft.auth.entity.AuditLog;
import com.kynsoft.auth.entity.Role;
import com.kynsoft.auth.entity.User;
import com.kynsoft.auth.enums.RoleName;
import com.kynsoft.auth.enums.UserStatus;
import com.kynsoft.auth.exception.DuplicateEmailException;
import com.kynsoft.auth.exception.UserNotFoundException;
import com.kynsoft.auth.repository.AuditLogRepository;
import com.kynsoft.auth.repository.RoleRepository;
import com.kynsoft.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuditLogRepository auditLogRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private HttpServletRequest httpServletRequest;

    @InjectMocks
    private UserServiceImpl userService;

    private UUID userId;
    private User testUser;
    private Role adminRole;
    private CreateUserRequest createRequest;
    private UpdateUserRequest updateRequest;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        adminRole = Role.builder().id(1).name(RoleName.ADMIN).build();
        testUser = User.builder()
                .id(userId)
                .email("test@example.com")
                .firstName("Test")
                .lastName("User")
                .passwordHash("hashed_password")
                .status(UserStatus.ACTIVE)
                .roles(new HashSet<>(Set.of(adminRole)))
                .createdAt(LocalDateTime.now())
                .lastLogin(LocalDateTime.now())
                .build();

        createRequest = CreateUserRequest.builder()
                .email("newuser@example.com")
                .password("Password123!")
                .firstName("New")
                .lastName("User")
                .roleName(RoleName.ESTUDIANTE)
                .build();

        updateRequest = UpdateUserRequest.builder()
                .email("updated@example.com")
                .firstName("Updated")
                .lastName("User")
                .build();
    }

    private void setupHttpRequest() {
        doReturn("127.0.0.1").when(httpServletRequest).getRemoteAddr();
        doReturn(null).when(httpServletRequest).getHeader(anyString());
    }

    // findAll tests
    @Test
    void testFindAllWithNullStatusFilter() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> userPage = new PageImpl<>(List.of(testUser), pageable, 1);
        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<UserResponse> result = userService.findAll(pageable, null);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("test@example.com", result.getContent().get(0).getEmail());
    }

    @Test
    void testFindAllEmptyResult() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<User> emptyPage = new PageImpl<>(List.of(), pageable, 0);
        when(userRepository.findAll(pageable)).thenReturn(emptyPage);

        Page<UserResponse> result = userService.findAll(pageable, null);

        assertNotNull(result);
        assertEquals(0, result.getTotalElements());
    }

    // findById tests
    @Test
    void testFindByIdSuccess() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        UserResponse result = userService.findById(userId);

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test", result.getFirstName());
    }

    @Test
    void testFindByIdNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findById(userId));
    }

    // createUser tests
    @Test
    void testCreateUserSuccess() {
        setupHttpRequest();
        Role studentRole = Role.builder().id(2).name(RoleName.ESTUDIANTE).build();
        User newUser = User.builder()
                .id(UUID.randomUUID())
                .email(createRequest.getEmail())
                .firstName(createRequest.getFirstName())
                .lastName(createRequest.getLastName())
                .status(UserStatus.ACTIVE)
                .roles(new HashSet<>(Set.of(studentRole)))
                .build();

        when(userRepository.existsByEmail(createRequest.getEmail())).thenReturn(false);
        when(roleRepository.findByName(RoleName.ESTUDIANTE)).thenReturn(Optional.of(studentRole));
        when(passwordEncoder.encode(createRequest.getPassword())).thenReturn("encoded_password");
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(null);

        UserResponse result = userService.createUser(createRequest, httpServletRequest);

        assertNotNull(result);
        assertEquals(createRequest.getEmail(), result.getEmail());
        verify(userRepository).existsByEmail(createRequest.getEmail());
        verify(roleRepository).findByName(RoleName.ESTUDIANTE);
        verify(passwordEncoder).encode(createRequest.getPassword());
    }

    @Test
    void testCreateUserDuplicateEmail() {
        when(userRepository.existsByEmail(createRequest.getEmail())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> userService.createUser(createRequest, httpServletRequest));
    }

    // updateUser tests
    @Test
    void testUpdateUserSuccess() {
        setupHttpRequest();
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmail(updateRequest.getEmail())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(null);

        UserResponse result = userService.updateUser(userId, updateRequest, httpServletRequest);

        assertNotNull(result);
        verify(userRepository).findById(userId);
        verify(userRepository).existsByEmail(updateRequest.getEmail());
    }

    @Test
    void testUpdateUserDuplicateEmail() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRepository.existsByEmail(updateRequest.getEmail())).thenReturn(true);

        assertThrows(DuplicateEmailException.class, () -> userService.updateUser(userId, updateRequest, httpServletRequest));
    }

    // changeStatus tests
    @Test
    void testChangeStatusSuccess() {
        setupHttpRequest();
        ChangeStatusRequest statusRequest = ChangeStatusRequest.builder()
                .status(UserStatus.BLOCKED)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(null);

        userService.changeStatus(userId, statusRequest, httpServletRequest);

        verify(userRepository).findById(userId);
        verify(userRepository).save(any(User.class));
        verify(auditLogRepository).save(any(AuditLog.class));
    }

    @Test
    void testChangeStatusUserNotFound() {
        ChangeStatusRequest statusRequest = ChangeStatusRequest.builder()
                .status(UserStatus.INACTIVE)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.changeStatus(userId, statusRequest, httpServletRequest));
    }

    // assignRole tests
    @Test
    void testAssignRoleSuccess() {
        setupHttpRequest();
        Role instructorRole = Role.builder().id(3).name(RoleName.INSTRUCTOR).build();
        testUser.setRoles(new HashSet<>(Set.of(adminRole)));
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(roleRepository.findByName(RoleName.INSTRUCTOR)).thenReturn(Optional.of(instructorRole));
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(auditLogRepository.save(any(AuditLog.class))).thenReturn(null);

        userService.assignRole(userId, RoleName.INSTRUCTOR, httpServletRequest);

        verify(userRepository).findById(userId);
        verify(roleRepository).findByName(RoleName.INSTRUCTOR);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testAssignRoleUserNotFound() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.assignRole(userId, RoleName.INSTRUCTOR, httpServletRequest));
    }
}
