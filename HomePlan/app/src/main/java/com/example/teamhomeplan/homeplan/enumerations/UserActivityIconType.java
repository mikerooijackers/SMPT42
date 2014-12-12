package com.example.teamhomeplan.homeplan.enumerations;

/**
 * Created by Niek on 12/12/14.
 *
 * Enumerations for the Icon type of the user activity.
 */
public enum UserActivityIconType {
    Dog(1),
    Food(2),
    Child(3),
    Workout(4),
    Doctor(5);


    private final int key;

    UserActivityIconType(int key)
    {
        this.key= key;
    }

    public int getKey()
    {
        return this.key;
    }

    public static UserActivityIconType fromKey(int key)
    {
        for(UserActivityIconType state : UserActivityIconType.values())
        {
            if(state.getKey() == key)
            {
                return state;
            }
        }
        return null;
    }

}
