package com.repositories;

import com.models.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilageRepository extends JpaRepository<Privilege, Long> {
    Privilege findByAuthority(String authority);
}
