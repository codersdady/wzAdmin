package com.wzAdmin.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReportNum implements Serializable {
    private String userid;
    private String name;
    private String num;
}
