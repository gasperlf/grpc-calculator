package net.ontopsolutions.calculator.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.ontopsolutions.calculator.CalculatorServiceGrpc;
import net.ontopsolutions.calculator.SumRequest;
import net.ontopsolutions.calculator.SumResponse;

public class CalculatorClient {

    public static void main(String[] args) {

        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        CalculatorServiceGrpc
                .CalculatorServiceBlockingStub stub = CalculatorServiceGrpc
                .newBlockingStub(managedChannel);

        SumRequest request = SumRequest
                .newBuilder()
                .setFirstNumber(10)
                .setSecondNumber(5)
                .build();
       SumResponse response = stub.sum(request);

        System.out.println(String.format("%s + %s = %s",request.getFirstNumber(),request.getSecondNumber(), response.getResult()));

        managedChannel.shutdown();
    }
}
