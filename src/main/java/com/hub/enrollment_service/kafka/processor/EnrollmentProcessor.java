package com.hub.enrollment_service.kafka.processor;

import com.hub.enrollment_service.grpc.CourseGrpcClient;
import com.hub.enrollment_service.kafka.event.EnrollmentCompletedEvent;
import com.hub.enrollment_service.kafka.event.OrderPaidEvent;
import com.hub.enrollment_service.model.dto.EnrollmentAllowedModulePostDto;
import com.hub.enrollment_service.model.dto.EnrollmentDetailGetDto;
import com.hub.enrollment_service.model.dto.EnrollmentPostDto;
import com.hub.enrollment_service.model.enumeration.EnrollmentStatus;
import com.hub.enrollment_service.model.enumeration.EnrollmentType;
import com.hub.enrollment_service.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class EnrollmentProcessor {

    private final CourseGrpcClient courseGrpcClient;
    private final EnrollmentService enrollmentService;

    @Bean
    public Function<OrderPaidEvent, EnrollmentCompletedEvent> processEnrollment() {

        return orderPaidEvent -> {

            // Get the order information
            String studentId = orderPaidEvent.studentId();
            List<Long> courseIds = orderPaidEvent.courseIds();

            // Get list of course modules via gRPC
            List<Long> enrollmentIds = new ArrayList<>();
            for (Long courseId : courseIds) {

                // Set allowed modules DTO
                List<EnrollmentAllowedModulePostDto> enrollmentAllowedModulePostDtos = new ArrayList<>();
                List<Long> moduleIds = courseGrpcClient.getModuleIds(courseId).getModuleIdsList();
                moduleIds.forEach(
                    moduleId ->
                            enrollmentAllowedModulePostDtos.add(new EnrollmentAllowedModulePostDto(moduleId))
                );

                // Set enrollments DTO
                EnrollmentPostDto enrollmentPostDto = new EnrollmentPostDto(
                        studentId,
                        courseId,
                        EnrollmentType.FULL,
                        EnrollmentStatus.ACTIVE,
                        enrollmentAllowedModulePostDtos
                );

                // Create one enrollment for one courseIds
                EnrollmentDetailGetDto enrollmentDetailGetDto
                        = enrollmentService.createEnrollment(enrollmentPostDto);
                enrollmentIds.add(enrollmentDetailGetDto.enrollmentId());
            }

            return new EnrollmentCompletedEvent(enrollmentIds, studentId, courseIds);
        };

    }

}
