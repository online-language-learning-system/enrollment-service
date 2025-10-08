package com.hub.enrollment_service.kafka.event;

import java.util.List;

public record EnrollmentCompletedEvent(
        List<Long> enrollmentIds,
        String studentId,
        List<Long> courseIds
) {
}
