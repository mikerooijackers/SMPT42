package com.example.teamhomeplan.homeplan.callback;

import com.example.teamhomeplan.homeplan.domain.Plan;
import com.example.teamhomeplan.homeplan.exception.ServiceException;

/**
 * Created by Niek on 19/01/15.
 *
 * Callback for plan generation
 */
public interface PlanGenerationCallback {
    void afterSuccessfulGeneration(Plan plan);

    void afterGenerationFailed(ServiceException se);
}
