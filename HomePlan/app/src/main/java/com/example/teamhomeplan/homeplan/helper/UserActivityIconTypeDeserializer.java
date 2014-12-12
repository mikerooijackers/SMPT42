package com.example.teamhomeplan.homeplan.helper;

import com.example.teamhomeplan.homeplan.enumerations.UserActivityIconType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Niek on 12/12/14.
 *
 * Deserializer for the UserActivityIconType enumeration.
 */
public class UserActivityIconTypeDeserializer implements JsonDeserializer<UserActivityIconType> {

    @Override
    public UserActivityIconType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int key = json.getAsInt();
        return UserActivityIconType.fromKey(key);
    }
}
