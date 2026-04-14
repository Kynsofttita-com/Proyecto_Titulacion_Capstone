package com.kynsoft.msauth.repository;

import com.kynsoft.msauth.entity.Role;
import com.kynsoft.msauth.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(RoleName name);
}
