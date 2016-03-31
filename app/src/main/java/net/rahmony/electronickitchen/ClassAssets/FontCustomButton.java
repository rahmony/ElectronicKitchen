package net.rahmony.electronickitchen.ClassAssets;

import android.content.Context;

import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import com.gc.materialdesign.views.ButtonFlat;
/**
 * Created by pc on 31/03/16.
 */
public class FontCustomButton extends Button {


    public FontCustomButton(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");

        this.setTypeface(face);
    }

    public FontCustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");
        ButtonFlat buttonFlat = new ButtonFlat(context,attrs);

        this.setTypeface(face);
    }


    public FontCustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");
        this.setTypeface(face);
    }



}
