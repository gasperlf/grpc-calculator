package net.ontopsolutions.calculator.server;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.ontopsolutions.calculator.CalculatorServiceGrpc;
import net.ontopsolutions.calculator.SquareRootRequest;
import net.ontopsolutions.calculator.SquareRootResponse;
import net.ontopsolutions.calculator.SumRequest;
import net.ontopsolutions.calculator.SumResponse;

public class CalculatorServiceImpl extends CalculatorServiceGrpc.CalculatorServiceImplBase {

    @Override
    public void sum(SumRequest request, StreamObserver<SumResponse> responseObserver) {
        SumResponse sumResponse = SumResponse
                .newBuilder()
                .setResult(sum(request))
                .build();
        responseObserver.onNext(sumResponse);

        responseObserver.onCompleted();
    }

    private int sum(SumRequest sumRequest) {
        return sumRequest.getFirstNumber() + sumRequest.getSecondNumber();
    }

    @Override
    public void squareRoot(SquareRootRequest request, StreamObserver<SquareRootResponse> responseObserver) {

        if (request.getNumber() >= 0) {
            double numberRoot = Math.sqrt(request.getNumber());
            responseObserver.onNext(SquareRootResponse.newBuilder().setNumberRoot(numberRoot).build());
            responseObserver.onCompleted();
        } else {
            responseObserver
                    .onError(Status.INVALID_ARGUMENT
                            .withDescription("The number being sent is not positive")
                            .augmentDescription("Number sent " + request.getNumber())
                    .asRuntimeException());
        }
    }
}
