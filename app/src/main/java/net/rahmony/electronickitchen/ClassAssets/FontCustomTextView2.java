package net.rahmony.electronickitchen.ClassAssets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by pc on 08/04/16.
 */
public class FontCustomTextView2 extends TextView{

    public FontCustomTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);


        setCustomFont(context);

    }


    private void setCustomFont(Context context) {
        try {
            //get The Default Language
            String lang = Locale.getDefault().getDisplayLanguage();


            //set TypeFace English as Default
            Typeface face = Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");

            //Check if The Default Language is Arabic or English
            if (lang == "العربية") {
                face = Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");
            } else if (lang == "ENGLISH" ) {
                face = Typeface.createFromAsset(context.getAssets(), "fonts/english_1.ttf");
            }

            this.setTypeface(face);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
