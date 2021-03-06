package com.example.teamhomeplan.homeplan.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.util.Base64;
import android.widget.TimePicker;

import com.example.teamhomeplan.homeplan.R;
import com.example.teamhomeplan.homeplan.domain.User;
import com.example.teamhomeplan.homeplan.enumerations.UserActivityIconType;

import java.io.ByteArrayOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Niek on 12/12/2014.
 *
 * Utility class for various helper functions
 */
public class Utilities {

    /**
     * Checks whether or not a String is a valid email
     * @param email The string to validate
     * @return True if valid, false when not
     */
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    /**
     * Encodes a Bitmap image to a base64 string
     * @param image The Bitmap image to encode
     * @return the encoded base64 string
     */
    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 20, baos);
        byte[] b = baos.toByteArray();
        int flags = Base64.DEFAULT;
        String imageEncoded = Base64.encodeToString(b,flags);

        return imageEncoded;
    }

    /**
     * Decode a base64 string to a bitmap
     * @param input The base64 string
     * @return The resulting Bitmap
     */
    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    /**
     * Get the URL for a user avatar.
     * @param user the user to get the avatar for
     * @return the URL as a string
     */
    public static String getAvatarUrl(User user)
    {
        if(user == null) throw new IllegalArgumentException("User was null");

        if(user.getAvatarUrl() == null || user.getAvatarUrl().equals(""))
        {
            return null;
        } else {
            return Constants.webservicebase +"images/" + user.getAvatarUrl();
        }
    }

    /**
     * Try if a string can be parsed to an int
     * @param value The String value to parse
     * @return true if parsed, false if not a number
     */
    public static boolean tryParseInt(String value)
    {
        try{
            //noinspection ResultOfMethodCallIgnored
            Integer.parseInt(value);
            return true;
        }catch (NumberFormatException nfe)
        {
            return false;
        }
    }

    /**
     * Set the ringer mode for the phone
     * @param context
     */
    public static void setSilentMode(Context context) {
        AudioManager am;
        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        am.setRingerMode(AudioManager.RINGER_MODE_SILENT);
    }

    /**
     * Get the current ringer mode of the phone
     * @param context
     * @return the current ringer mode
     */
    public static int getRingerMode(Context context) {
        AudioManager am;
        am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return am.getRingerMode();
    }

    public static Drawable getUserActivityIcon(Context context,
                                               UserActivityIconType iconType) {
        //TODO: implement

        int drawableId = R.drawable.logo;
        switch(iconType)
        {
            case CHILD:
                drawableId = R.drawable.kids1;
                break;
            case DOG:
                drawableId = R.drawable.dog1;
                break;
            case FOOD:
                drawableId = R.drawable.lunch1;
                break;
            case DOCTOR:
                //TODO:
                break;
            case WORKOUT:
                //TODO:
                break;

        }

        return context.getResources().getDrawable(drawableId);
    }

    public static long getTimePickerInMilliseconds(TimePicker tp)
    {
        return (tp.getCurrentHour() * 3600000) + (tp.getCurrentMinute() * 60000);
    }
}
