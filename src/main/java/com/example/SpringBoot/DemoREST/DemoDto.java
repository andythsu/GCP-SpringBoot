package com.example.SpringBoot.DemoREST;

import com.google.cloud.Timestamp;

/**
 * @Author: Andy Su
 * @Date: 2018-11-10
 */
public class DemoDto {
    private Timestamp CreatedAt;
    private String Type;
    private String Name;
    private String Value;
    public static String CreatedAtCol = "CreatedAt";
    public static String TypeCol = "Type";
    public static String NameCol = "Name";
    public static String ValueCol = "Value";

    public DemoDto(Timestamp createdAt, String type, String name, String value){
        this.CreatedAt = createdAt;
        this.Type = type;
        this.Name = name;
        this.Value = value;
    }

    public void setCreatedAt(Timestamp createdAt) {
        CreatedAt = createdAt;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setValue(String value) {
        Value = value;
    }

    public Timestamp getCreatedAt() {
        return CreatedAt;
    }

    public String getType() {
        return Type;
    }

    public String getName() {
        return Name;
    }

    public String getValue() {
        return Value;
    }
}
