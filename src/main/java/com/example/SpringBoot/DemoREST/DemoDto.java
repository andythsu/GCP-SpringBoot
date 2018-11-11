package com.example.SpringBoot.DemoREST;

import com.google.cloud.Timestamp;

import java.util.Date;

/**
 * @Author: Andy Su
 * @Date: 2018-11-10
 */
public class DemoDto {
    private String Type;
    private String Name;
    private String Value;
    private Date CreatedAt;
    public static String CreatedAtCol = "CreatedAt";
    public static String TypeCol = "Type";
    public static String NameCol = "Name";
    public static String ValueCol = "Value";
    public static String GenName = "Generic_Name";
    public static String GenType = "Generic_Type";
    public static String GenValue = "Generic_Value";

    public DemoDto(Timestamp createdAt, String type, String name, String value){
        this.CreatedAt = createdAt.toDate();
        this.Type = type;
        this.Name = name;
        this.Value = value;
    }

    public void setCreatedAt(Date createdAt) {
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

    public Date getCreatedAt() {
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
