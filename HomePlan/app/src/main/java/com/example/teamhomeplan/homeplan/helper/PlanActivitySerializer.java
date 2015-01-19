package com.example.teamhomeplan.homeplan.helper;

import com.example.teamhomeplan.homeplan.enumerations.PlanActivityType;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

/**
 * Created by Niek on 19/01/15.
 *
 * Class for Serializing a PlanActivityType to json
 */
public class PlanActivitySerializer implements JsonSerializer<PlanActivityType> {
    @Override
    public JsonElement serialize(PlanActivityType src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.getKey());
    }
}
