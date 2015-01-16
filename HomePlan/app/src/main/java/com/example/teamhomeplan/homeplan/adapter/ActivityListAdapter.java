package com.example.teamhomeplan.homeplan.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teamhomeplan.homeplan.R;
import com.example.teamhomeplan.homeplan.domain.UserActivity;
import com.example.teamhomeplan.homeplan.helper.Utilities;

import java.util.List;

/**
 * Created by Niek on 15/01/15.
 *
 * ListAdapter for a list of useractivities.
 */
public class ActivityListAdapter extends ArrayAdapter<UserActivity> {

    private Activity context;
    private List<UserActivity> userActivities;

    public ActivityListAdapter(Activity context, List<UserActivity> userActivityList) {
        super(context, R.layout.listitem_useractivity, userActivityList);

        if(context == null)
        {
            throw new IllegalArgumentException("contxt");
        }

        if(userActivityList == null)
        {
            throw new IllegalArgumentException("userActivityList");
        }


        this.context = context;
        userActivities = userActivityList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View returnView = convertView;

        if(returnView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            returnView = inflater.inflate(R.layout.listitem_useractivity, null);
        }

        UserActivity userActivity = userActivities.get(position);
        TextView userActivityName = (TextView) returnView.findViewById(R.id.list_item_activity_name);
        ImageView userActivityIcon = (ImageView) returnView.findViewById(R.id.listitem_useractivity_icon);

        userActivityName.setText(userActivity.getName());
        userActivityIcon.setImageDrawable(Utilities.getUserActivityIcon(getContext(), userActivity.getIconType()));

        return returnView;
    }
}
