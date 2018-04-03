package com.andreas.wbl;

/**
 * Created by Andreas on 11/3/2018.
 */

public class Customer {

    //fields
    private int id;
    private String customerName;
    private String customerSurname;
    private int customerIdCard;
    private int customerBirthdate;
    private String customerAddress;
    private int customerZipCode;

    //default constructor
    public Customer(int id, String customerName, String customerSurname, int customerIdCard, int customerBirthdate, String customerAddress, int customerZipCode) {
        this.id = id;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
        this.customerIdCard = customerIdCard;
        this.customerBirthdate = customerBirthdate;
        this.customerAddress = customerAddress;
        this.customerZipCode = customerZipCode;
    }

    //methods set,get
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerSurname(){
        return customerSurname;
    }

    public void setCustomerSurname(String customerSurname){
        this.customerSurname = customerSurname;
    }

    public int getCustomerIdCard(){
        return customerIdCard;
    }

    public void setCustomerIdCard(int customerIdCard){
        this.customerIdCard= customerIdCard;
    }

    public int getCustomerBirthdate(){
        return customerBirthdate;
    }

    public void setCustomerBirthdate(int customerBirthdate){
        this.customerBirthdate = customerBirthdate;
    }

    public String getCustomerAddress(){
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress){
        this.customerAddress = customerAddress;
    }

    public int getCustomerZipCode() {
        return customerZipCode;
    }

    public void setCustomerZipCode(int customerZipCode) {
        this.customerZipCode = customerZipCode;
    }

}