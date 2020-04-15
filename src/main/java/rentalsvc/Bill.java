package rentalsvc;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Bill_table")
public class Bill {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long amount;
    private Long orderId;

    @PostPersist
    public void when_PostPersist_publishEvent(){
        FeeReceived feeReceived = new FeeReceived();
        BeanUtils.copyProperties(this, feeReceived);
        feeReceived.publish();

        FeeRefundCompleted feeRefundCompleted = new FeeRefundCompleted();
        BeanUtils.copyProperties(this, feeRefundCompleted);
        feeRefundCompleted.publish();

    }

//
//    @PostPersist
//    public void publishFeeReceived(){
//
//        FeeReceived feeReceived = new FeeReceived();
//        BeanUtils.copyProperties(this, feeReceived);
//        feeReceived.publish();
//
//    }
//
//    @PostPersist
//    public void publishFeeRefundCompleted(){
//
//        FeeRefundCompleted feeRefundCompleted = new FeeRefundCompleted();
//        BeanUtils.copyProperties(this, feeRefundCompleted);
//        feeRefundCompleted.publish();
//
//    }
//

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

}
