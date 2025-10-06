package com.hub.enrollment_service.model.dto;

import com.hub.enrollment_service.model.enumeration.EnrollmentStatus;
import com.hub.enrollment_service.model.enumeration.EnrollmentType;

import java.util.List;

public record EnrollmentPostDto (
        Long courseId,
        EnrollmentType enrollmentType,
        EnrollmentStatus enrollmentStatus,
        String allowedModules,
        List<EnrollmentAllowedModulePostDto> enrollmentAllowedModulePostDtos
) {
}
