package com.hub.enrollment_service.model.dto;

import com.hub.enrollment_service.model.Enrollment;
import com.hub.enrollment_service.model.EnrollmentAllowedModule;
import com.hub.enrollment_service.model.enumeration.EnrollmentStatus;
import com.hub.enrollment_service.model.enumeration.EnrollmentType;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;

public record EnrollmentDetailGetDto(
        Long enrollmentId,
        String studentId,
        Long courseId,
        EnrollmentType enrollmentType,
        EnrollmentStatus enrollmentStatus,

        // Note to fix
        List<Long> allowedModuleId,

        OffsetDateTime remainingDay
) {
    public static EnrollmentDetailGetDto fromModel(Enrollment enrollment) {

        OffsetDateTime remaining = null;
        if (enrollment.getTrialEndOn() != null) {
            Duration duration = Duration.between(OffsetDateTime.now(), enrollment.getTrialEndOn());
            remaining = OffsetDateTime.ofInstant(java.time.Instant.ofEpochSecond(duration.getSeconds()), OffsetDateTime.now().getOffset());
        }

        return new EnrollmentDetailGetDto(
                enrollment.getId(),
                enrollment.getStudentId(),
                enrollment.getCourseId(),
                enrollment.getEnrollmentType(),
                enrollment.getEnrollmentStatus(),
                enrollment.getAllowedModules().stream().map(EnrollmentAllowedModule::getId).toList(),
                remaining
        );
    }
}
