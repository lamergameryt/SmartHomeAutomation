package com.lamergameryt.fdwebview.mysql;

import org.json.JSONPropertyName;

public class Models {

    public record Setting(
        DatabaseHandler.Location location,
        @JSONPropertyName("user_id") int userId,
        @JSONPropertyName("pin_id") int pinId,
        @JSONPropertyName("value") int value
    ) {}

    public record User(
        @JSONPropertyName("id") int id,
        @JSONPropertyName("name") String name,
        @JSONPropertyName("day") int day,
        @JSONPropertyName("month") int month,
        @JSONPropertyName("year") int year
    ) {}
}
