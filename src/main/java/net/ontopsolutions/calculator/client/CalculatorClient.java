package net.ontopsolutions.calculator.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import net.ontopsolutions.calculator.CalculatorServiceGrpc;
import net.ontopsolutions.calculator.SquareRootRequest;
import net.ontopsolutions.calculator.SumRequest;
import net.ontopsolutions.calculator.SumResponse;

public class CalculatorClient {

    public static void main(String[] args) {

        CalculatorClient client = new CalculatorClient();
        ManagedChannel managedChannel =  client.createChannel();
        //client.sumServer(managedChannel);
        client.squareRootServer(managedChannel);
        managedChannel.shutdown();
    }

    private void squareRootServer(ManagedChannel channel){

        CalculatorServiceGrpc
                .CalculatorServiceBlockingStub blokingStub = CalculatorServiceGrpc
                .newBlockingStub(channel);

        int number= -1;

        try{
            blokingStub.squareRoot(SquareRootRequest.newBuilder().setNumber(number).build());
        }catch (StatusRuntimeException e){
            System.out.println("Got an error from squareRootServer");
            e.printStackTrace();
        }


    }

    private void sumServer(ManagedChannel channel){

        CalculatorServiceGrpc
                .CalculatorServiceBlockingStub stub = CalculatorServiceGrpc
                .newBlockingStub(channel);

        SumRequest request = SumRequest
                .newBuilder()
                .setFirstNumber(10)
                .setSecondNumber(5)
                .build();
        SumResponse response = stub.sum(request);

        System.out.println(String.format("%s + %s = %s",request.getFirstNumber(),request.getSecondNumber(), response.getResult()));

    }

    private ManagedChannel createChannel(){
       return ManagedChannelBuilder
                .forAddress("localhost", 50051)
                .usePlaintext()
                .build();
    }
}
