package cn.codercheng.pojo;

/**
 * @ClassName Order
 * @Description:
 * @Author CoderCheng
 * @Date 2021-01-26 14:26
 * @Version V1.0
 **/

public class Order {


    private long orderId;


    private String status;


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
