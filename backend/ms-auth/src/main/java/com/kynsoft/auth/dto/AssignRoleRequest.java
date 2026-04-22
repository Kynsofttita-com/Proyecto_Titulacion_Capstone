package com.kynsoft.auth.dto;

import com.kynsoft.auth.enums.RoleName;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignRoleRequest {
    @NotNull(message = "Role name is required")
    private RoleName roleName;
}
