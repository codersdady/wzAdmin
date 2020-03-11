package com.wzAdmin.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginNum implements Serializable {
    private int id;
    private String userid;
    private int num;
}
