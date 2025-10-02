package com.hub.enrollment_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "dto", name = "enrollment_allowed_module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentAllowedModule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_id")
    private Long moduleId;

    @ManyToOne()
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

}
