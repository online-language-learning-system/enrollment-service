package com.hub.enrollment_service.grpc;

import com.hub.enrollment_service.grpc.module.CourseIdRequest;
import com.hub.enrollment_service.grpc.module.ModuleListResponse;
import com.hub.enrollment_service.grpc.module.ModuleServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class CourseGrpcClient {

    private ManagedChannel channel;
    private ModuleServiceGrpc.ModuleServiceBlockingStub stub;

    @PostConstruct
    public void init() {
        channel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        // Blocking Stub: waiting for response
        stub = ModuleServiceGrpc.newBlockingStub(channel);
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null) channel.shutdown();
    }

    public ModuleListResponse getModuleIds(Long courseId) {
        CourseIdRequest request = CourseIdRequest.newBuilder()
                .setCourseId(courseId)
                .build();
        return stub.getModuleIdsForCourse(request);
    }
}
