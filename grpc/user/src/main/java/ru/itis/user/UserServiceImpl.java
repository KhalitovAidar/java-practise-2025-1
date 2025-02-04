package ru.itis.user;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.user.proto.UserRequest;
import ru.itis.user.proto.UserResponse;
import ru.itis.user.proto.CreateUserRequest;
import ru.itis.user.proto.UserServiceGrpc;

@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void getUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserResponse response = UserResponse.newBuilder()
                .setUserId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createUser(CreateUserRequest request, StreamObserver<UserResponse> responseObserver) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        user = userRepository.save(user);

        UserResponse response = UserResponse.newBuilder()
                .setUserId(user.getId())
                .setName(user.getName())
                .setEmail(user.getEmail())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}