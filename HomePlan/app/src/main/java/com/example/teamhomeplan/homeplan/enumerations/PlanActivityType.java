package com.example.teamhomeplan.homeplan.enumerations;

/**
 * Created by Niek on 19/01/15.
 *
 * Type for the planActivity as described in backend
 */
public enum PlanActivityType {
    PERSONAL(1),
    INCIDENTAL(2),
    WORKRELATED(3);

    private final int key;

    PlanActivityType(int key)
    {
        this.key= key;
    }

    public int getKey()
    {
        return this.key;
    }

    public static PlanActivityType fromKey(int key)
    {
        for(PlanActivityType state : PlanActivityType.values())
        {
            if(state.getKey() == key)
            {
                return state;
            }
        }
        return null;
    }


}
