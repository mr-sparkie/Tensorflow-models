package com.hightech.bean;

public class User {
    private int accNo;
    private String fullName;
    private String address;
    private String mobileNo;
    private String email;
    private String accType;
    private String dob;
    private String idProof;
    private int initialBalance;

    public User(int accNo, String fullName, String address, String mobileNo, String email, String accType, String dob, String idProof, int initialBalance) {
        this.accNo = accNo;
        this.fullName = fullName;
        this.address = address;
        this.mobileNo = mobileNo;
        this.email = email;
        this.accType = accType;
        this.dob = dob;
        this.idProof = idProof;
        this.initialBalance = initialBalance;
    }

    // Getters and Setters
    public int getAccNo() {
        return accNo;
    }

    public void setAccNo(int accNo) {
        this.accNo = accNo;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public int getInitialBalance() {
        return initialBalance;
    }

    public void setInitialBalance(int initialBalance) {
        this.initialBalance = initialBalance;
    }

    @Override
    public String toString() {
        return "User{" +
                "accNo=" + accNo +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", email='" + email + '\'' +
                ", accType='" + accType + '\'' +
                ", dob='" + dob + '\'' +
                ", idProof='" + idProof + '\'' +
                ", initialBalance=" + initialBalance +
                '}';
    }
}
