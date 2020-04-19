package rentalsvc;

import rentalsvc.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalRequestOccured_ChargeFree(@Payload RentalRequested rentalRequested){

        if(rentalRequested.isMe()){
            System.out.println("##### listener  랜트요청 이벤트 수신(RentalRequested) : " + rentalRequested.toJson());
            
           FeeReceived feeReceived = new FeeReceived();
            
	           feeReceived.setOrderId(rentalRequested.getOrderId());
	           feeReceived.setAmount(rentalRequested.getAmount());
	           feeReceived.publish();
	            
           System.out.println("##### 요금수납 완료 이벤트 발송 : (FeeReceived)");
        }
    }
	
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRentalCancellationOccured_FreeRefund(@Payload RentalCancellationOccured rentalCancellationOccured){

        if(rentalCancellationOccured.isMe()){
            System.out.println("##### listener  환불요 이벤트 수신(RentalCancellationOccured) : " + rentalCancellationOccured.toJson());
            
            FeeRefundCompleted feeRefundCompleted = new FeeRefundCompleted();
            
            feeRefundCompleted.setOrderId(rentalCancellationOccured.getOrderId());
            feeRefundCompleted.publish();
            
           System.out.println("##### 환불처리 완료 이벤트 발송 : (FeeRefundCompleted)");
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheninventoryStockShortageOccured_FreeRefund(@Payload InventoryStockShortage inventoryStockShortage){

        if(inventoryStockShortage.isMe()){
            System.out.println("##### listener  재고부족 이벤트 수신(InventoryStockShortage) : " + inventoryStockShortage.toJson());
            
            FeeRefundCompleted feeRefundCompleted = new FeeRefundCompleted();
            
            feeRefundCompleted.setOrderId(inventoryStockShortage.getOrderId());
            feeRefundCompleted.publish();
            
           System.out.println("##### 환불처리 완료 이벤트 발송 : (FeeRefundCompleted)");
        }
    }
}
