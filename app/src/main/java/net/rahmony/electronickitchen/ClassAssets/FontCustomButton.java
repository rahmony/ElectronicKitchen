package net.rahmony.electronickitchen.ClassAssets;

import android.content.Context;

import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.gc.materialdesign.views.ButtonFlat;
import com.gc.materialdesign.views.ButtonRectangle;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * Created by pc on 31/03/16.
 */
public class FontCustomButton extends ButtonRectangle {


    public FontCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context);
    }

    private void setCustomFont(Context context) {
        try {
            //get The Default Language
            // String lang = Locale.getDefault().getLanguage();


            //set TypeFace English as Default
            Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");

            //Check if The Default Language is Arabic or English
            /*if (lang == "ar") {
                face = Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");
            } else if(lang == "en") {
                face = Typeface.createFromAsset(context.getAssets(), "fonts/english_1.ttf");
            }*/


            Field field = ButtonRectangle.class.getDeclaredField("textButton");
            field.setAccessible(true);
            TextView textView = (TextView) field.get(this);
            textView.setTypeface(face);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
