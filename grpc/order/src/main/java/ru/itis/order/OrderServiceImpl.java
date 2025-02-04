package ru.itis.order;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import ru.itis.order.proto.OrderRequest;
import ru.itis.order.proto.OrderResponse;
import ru.itis.order.proto.CreateOrderRequest;
import ru.itis.order.proto.OrderServiceGrpc;
import ru.itis.order.proto.UserRequest;
import ru.itis.order.proto.UserResponse;
import ru.itis.order.proto.UserServiceGrpc;

@GrpcService
public class OrderServiceImpl extends OrderServiceGrpc.OrderServiceImplBase {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub;

    @Override
    public void getOrder(OrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        UserResponse userResponse = userServiceBlockingStub.getUser(
                UserRequest.newBuilder()
                        .setUserId(order.getUserId())
                        .build()
        );

        OrderResponse response = OrderResponse.newBuilder()
                .setOrderId(order.getId())
                .setProductName(order.getProductName())
                .setUserId(order.getUserId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<OrderResponse> responseObserver) {
        Order order = new Order();
        order.setProductName(request.getProductName());
        order.setUserId(request.getUserId());

        order = orderRepository.save(order);

        OrderResponse response = OrderResponse.newBuilder()
                .setOrderId(order.getId())
                .setProductName(order.getProductName())
                .setUserId(order.getUserId())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}