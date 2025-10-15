package com.dipak.service;

import com.dipak.dao.UserDAO;
import com.dipak.entity.Users;

import java.util.Scanner;

public class UserService {
    Scanner sc;
    Users users = new Users();
    UserDAO dao = new UserDAO();

    public UserService(){
    }

    public UserService(Scanner sc){
        this.sc=sc;
    }

    public void register(){
        System.out.println("\n*-------- Registration Form --------*\n");
        sc.nextLine();
        System.out.print("Full Name: ");
        String name = sc.nextLine();
        users.setName(name);
        System.out.print("Email: ");
        String email = sc.next();
        users.setEmail(email);
        phone();
        password();
        aadhaar();
        pan();
        sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        users.setAddress(address);

        try{
            dao.create(users);
            System.out.println("Registration Successful...");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void phone(){
        while (true){
            System.out.print("Phone Number: +91");
            String phone = sc.next();
            if (phone.matches("\\d{10}")){
                users.setPhone(phone);
                return;
            }else{
                System.out.println("Enter valid phone number...");
            }
        }
    }

    private void password(){
        while (true){
            System.out.println("Create 8 digit password");
            System.out.print("Password: ");
            String pass = sc.next();
            if (pass.length()>=8){
                System.out.print("Re-Enter Password: ");
                String rpass = sc.next();
                if (pass.equals(rpass)){
                    users.setPassword(pass);
                    return;
                }else {
                    System.out.println("Enter same password while re-entering password...\n");
                }
            }else{
                System.out.println("Enter minimum 8 digit password...\n");
            }
        }
    }

    private void aadhaar() {
        while (true) {
            System.out.print("Aadhaar Number (12 digits): ");
            String aadhaar = sc.next();
            if (aadhaar.matches("\\d{12}")) {
                users.setAadhaar(aadhaar);
                return;
            } else {
                System.out.println("Invalid Aadhaar number. Please enter exactly 12 digits.\n");
            }
        }
    }

    private void pan() {
        while (true) {
            System.out.print("PAN Number: ");
            String pan = sc.next();
            pan = pan.toUpperCase();
            if (pan.matches("(?i)[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
                users.setPan(pan);
                return;
            } else {
                System.out.println("Invalid PAN number. Please enter again (e.g., ABCDE1234F).");
            }
        }
    }
}
