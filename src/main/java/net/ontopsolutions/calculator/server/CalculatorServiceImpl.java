package net.ontopsolutions.calculator.server;

import io.grpc.stub.StreamObserver;
import net.ontopsolutions.calculator.CalculatorServiceGrpc;
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

    private int sum(SumRequest sumRequest){
        return sumRequest.getFirstNumber() + sumRequest.getSecondNumber();
    }
}
