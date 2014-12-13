package com.example.teamhomeplan.homeplan.enumerations;

/**
 * Created by Niek on 12/12/14.
 *
 * Enumerations for the Icon type of the user activity.
 */
public enum UserActivityIconType {
    NONE(0),
    DOG(1),
    FOOD(2),
    CHILD(3),
    WORKOUT(4),
    DOCTOR(5);


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
