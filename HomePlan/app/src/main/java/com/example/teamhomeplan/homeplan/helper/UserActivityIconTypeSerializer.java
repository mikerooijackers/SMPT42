package com.example.teamhomeplan.homeplan.helper;

import com.example.teamhomeplan.homeplan.enumerations.UserActivityIconType;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Niek on 12/12/14.
 *
 * Serializer for the UserActivityIconType enumerations
 */
public class UserActivityIconTypeSerializer implements JsonSerializer<UserActivityIconType> {
    @Override
    public JsonElement serialize(UserActivityIconType src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getKey());
    }
}
