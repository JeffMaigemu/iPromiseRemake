package maigemu.com.ipromiseremake.data;

import android.content.Context;
import android.graphics.Typeface;

import java.lang.reflect.Field;

/**
 * Created by JEFFREY on 01/04/2017.
 */

public class FontsOverride {


    public static void setDefaultFont(Context context,
                                      String nameOfFontBeingReplaced,
                                      String nameOfFontInAsset) {
        Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(),
                nameOfFontInAsset);
        replaceFont(nameOfFontBeingReplaced, customFontTypeface);
    }

    private static void replaceFont(String nameOfFontBeingReplaced,
                                    Typeface customFontTypeface) {
        try {
            Field staticField = Typeface.class
                    .getDeclaredField(nameOfFontBeingReplaced);
            staticField.setAccessible(true);
            staticField.set(null, customFontTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}