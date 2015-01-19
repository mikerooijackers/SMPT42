package com.example.teamhomeplan.homeplan.helper;

import com.example.teamhomeplan.homeplan.domain.PlanActivity;
import com.example.teamhomeplan.homeplan.enumerations.PlanActivityType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by Niek on 19/01/15.
 *
 * Class for deserializing a json integer to the PlanActivity Enum enum.
 */
public class PlanActivityTypeDeserializer implements JsonDeserializer<PlanActivityType> {
    @Override
    public PlanActivityType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        int key = json.getAsInt();
        return PlanActivityType.fromKey(key);
    }
}
