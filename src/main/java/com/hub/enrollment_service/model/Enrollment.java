package com.hub.enrollment_service.model;

import com.hub.common_library.model.AbstractAuditEntity;
import com.hub.enrollment_service.model.enumeration.EnrollmentStatus;
import com.hub.enrollment_service.model.enumeration.EnrollmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(schema = "dto", name = "enrollment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment extends AbstractAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "enrollment_type")
    private EnrollmentType enrollmentType;

    @Column(name = "enrollment_status")
    private EnrollmentStatus enrollmentStatus;

    @OneToMany(mappedBy = "enrollment")
    @Column(name = "allowed_modules")
    private List<EnrollmentAllowedModule> allowedModules;  // ["1", "2", "3"] or ["*"]

    @Column(name = "trial_start_on")
    private OffsetDateTime trialStartOn;

    @Column(name = "trial_end_on")
    private OffsetDateTime trialEndOn;

}
