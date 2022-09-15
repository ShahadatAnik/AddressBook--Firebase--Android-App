package edu.ewubd.addressbook;

public class AddressList {
    String addressId;
    String username, email, firstAddress, secondAddress, phone;

    public AddressList(String addressId, String username , String email, String firstAddress, String secondAddress, String phone) {
        this.addressId = addressId;
        this.username = username;
        this.email = email;
        this.firstAddress = firstAddress;
        this.secondAddress = secondAddress;
        this.phone = phone;
    }
    public AddressList(){}

    public String getId() {
        return addressId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstAddress() {
        return firstAddress;
    }

    public String getSecondAddress() {
        return secondAddress;
    }
    public String getPhone() {return phone;}


}

