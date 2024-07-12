package com.hightech.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transfer {
    private int transferId;
    private int senderAccNo;
//    private int receiverAccNo;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public Transfer(int transferId, int senderAccNo, BigDecimal amount, LocalDateTime timestamp) {
        this.transferId = transferId;
        this.senderAccNo = senderAccNo;
//        this.receiverAccNo = receiverAccNo;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }

    public int getSenderAccNo() {
        return senderAccNo;
    }

    public void setSenderAccNo(int senderAccNo) {
        this.senderAccNo = senderAccNo;
    }

//    public int getReceiverAccNo() {
//        return receiverAccNo;
//    }

//    public void setReceiverAccNo(int receiverAccNo) {
//        this.receiverAccNo = receiverAccNo;
//    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
