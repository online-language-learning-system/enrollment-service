package com.hub.enrollment_service.controller;

import com.hub.enrollment_service.model.dto.EnrollmentDetailGetDto;
import com.hub.enrollment_service.model.dto.EnrollmentPostDto;
import com.hub.enrollment_service.service.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping(path = "/storefront/enroll")
    public ResponseEntity<EnrollmentDetailGetDto> enrollCourse(@RequestBody EnrollmentPostDto enrollmentPostDto) {
        return ResponseEntity.ok(enrollmentService.createEnrollment(enrollmentPostDto));
    }
}
