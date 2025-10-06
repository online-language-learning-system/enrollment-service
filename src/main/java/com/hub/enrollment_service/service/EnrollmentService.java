package com.hub.enrollment_service.service;

import com.hub.enrollment_service.model.Enrollment;
import com.hub.enrollment_service.model.EnrollmentAllowedModule;
import com.hub.enrollment_service.model.dto.EnrollmentDetailGetDto;
import com.hub.enrollment_service.model.dto.EnrollmentPostDto;
import com.hub.enrollment_service.model.enumeration.EnrollmentStatus;
import com.hub.enrollment_service.model.enumeration.EnrollmentType;
import com.hub.enrollment_service.repository.EnrollmentAllowedModuleRepository;
import com.hub.enrollment_service.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentAllowedModuleRepository allowedModuleRepository;

    @Transactional
    public EnrollmentDetailGetDto createTrialEnrollment(EnrollmentPostDto enrollmentPostDto) {

        Enrollment enrollment = new Enrollment();
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        enrollment.setStudentId(userId);
        enrollment.setCourseId(enrollmentPostDto.courseId());
        enrollment.setEnrollmentType(enrollmentPostDto.enrollmentType());
        enrollment.setEnrollmentStatus(EnrollmentStatus.ACTIVE);

        // Set day to learn
        OffsetDateTime now = OffsetDateTime.now();
        enrollment.setTrialStartOn(OffsetDateTime.now());
        if (enrollment.getEnrollmentType().equals(EnrollmentType.TRIAL)) {
            // 3 days free trial
            enrollment.setTrialEndOn(now.plusDays(3));
        } else {
            enrollment.setTrialEndOn(null);
        }

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        enrollmentPostDto.enrollmentAllowedModulePostDtos().forEach(
            allowedModuleDto -> {
                EnrollmentAllowedModule allowedModule = setAllowedModule(allowedModuleDto.moduleId(), savedEnrollment);
                savedEnrollment.getAllowedModules().add(allowedModule);
            }
        );

        return EnrollmentDetailGetDto.fromModel(enrollmentRepository.save(savedEnrollment));
    }

    public EnrollmentAllowedModule setAllowedModule(Long moduleId, Enrollment enrollment) {
        EnrollmentAllowedModule allowedModule = new EnrollmentAllowedModule();
        allowedModule.setModuleId(moduleId);
        allowedModule.setEnrollment(enrollment);

        return allowedModuleRepository.save(allowedModule);
    }
}
