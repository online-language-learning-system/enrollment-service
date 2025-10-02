package com.hub.enrollment_service.repository;

import com.hub.enrollment_service.model.EnrollmentAllowedModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentAllowedModuleRepository
        extends JpaRepository<EnrollmentAllowedModule, Long> {
}
