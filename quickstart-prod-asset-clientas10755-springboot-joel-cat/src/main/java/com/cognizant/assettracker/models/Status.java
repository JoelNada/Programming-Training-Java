package com.cognizant.assettracker.models;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Status {
    private String status;
    private ArrayList<String> content;

}
