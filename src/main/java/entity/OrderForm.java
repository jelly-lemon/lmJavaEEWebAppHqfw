package entity;

import java.sql.Timestamp;

public class OrderForm {
    private int orderFormID;
    private Timestamp dateTime;
    private String shoppingList;
    private String buyerPhone;
    private String receiveName;
    private String receivePhone;
    private String receiveAddress;
    private String orderFormStatus;
    private float totalPrice;

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone;
    }

    public int getOrderFormID() {
        return orderFormID;
    }

    public void setOrderFormID(int orderFormID) {
        this.orderFormID = orderFormID;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(String shoppingList) {
        this.shoppingList = shoppingList;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public void setBuyerPhone(String buyerPhone) {
        this.buyerPhone = buyerPhone;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }

    public String getOrderFormStatus() {
        return orderFormStatus;
    }

    public void setOrderFormStatus(String orderFormStatus) {
        this.orderFormStatus = orderFormStatus;
    }
}
