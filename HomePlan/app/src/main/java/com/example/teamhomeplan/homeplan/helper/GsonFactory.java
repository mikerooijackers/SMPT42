package com.example.teamhomeplan.homeplan.helper;

import com.example.teamhomeplan.homeplan.enumerations.PlanActivityType;
import com.example.teamhomeplan.homeplan.enumerations.UserActivityIconType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Niek on 12/12/14.
 *
 * Class containing a static method for building our Gson object with our own TypeAddapters
 */
public class GsonFactory {

    /**
     * Static method that returns our own Gson object with custom type adapters
     * @return Custom Gson object
     */
    public static Gson createGson()
    {
        GsonBuilder builder = new GsonBuilder();

        //Register the Icon type deserializer
        builder.registerTypeAdapter(UserActivityIconType.class, new UserActivityIconTypeDeserializer());

        //Register the icon type serializer
        builder.registerTypeAdapter(UserActivityIconType.class, new UserActivityIconTypeSerializer());

        //Register the PlanActivityTpe deserializer
        builder.registerTypeAdapter(PlanActivityType.class, new PlanActivityTypeDeserializer());

        //Register the PlanActivityType serializer
        builder.registerTypeAdapter(PlanActivityType.class, new PlanActivitySerializer());

        return builder.create();
    }
}
