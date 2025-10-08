package com.hub.enrollment_service.model.enumeration;

public enum EnrollmentStatus {
    PENDING,    // chưa kích hoạt
    ACTIVE,     // đang còn hiệu lực
    EXPIRED,    // hết hạn (trial hết hạn hoặc full hết hạn gói)
    CANCELLED,  // học viên hủy giữa chừng
}
