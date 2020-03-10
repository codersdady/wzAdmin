package com.wzAdmin.model;

import lombok.Data;

import java.io.Serializable;
@Data
public class SystemUser implements Serializable {
    private String id;
    private String birthday;
    private String password;
    private String sex;
    private String name;
    private String role;
    private int status;
    private String email;
    private String url;
    private String ucreate;


    public interface Status{
        int DISABLE=0;
        int VALID=1;
        int LOCKED=2;
    }
}
