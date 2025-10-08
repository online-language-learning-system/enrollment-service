package com.hub.enrollment_service.kafka.producer.event;

import com.hub.enrollment_service.model.enumeration.EnrollmentType;

public record EnrollmentCompletedEvent(
        String studentId,
        Long courseId,
        EnrollmentType enrollmentType
) {
}
