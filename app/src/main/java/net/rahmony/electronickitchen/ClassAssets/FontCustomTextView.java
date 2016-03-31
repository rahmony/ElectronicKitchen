package net.rahmony.electronickitchen.ClassAssets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.gc.materialdesign.views.ButtonRectangle;
/**
 * Created by pc on 30/03/16.
 */
public class FontCustomTextView extends TextView  {

    public FontCustomTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");
        this.setTypeface(face);
    }

    public FontCustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");
        
        this.setTypeface(face);
    }

    public FontCustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");
        this.setTypeface(face);
    }



}

