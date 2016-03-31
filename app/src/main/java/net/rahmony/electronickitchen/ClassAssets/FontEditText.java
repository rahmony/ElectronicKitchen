package net.rahmony.electronickitchen.ClassAssets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by pc on 31/03/16.
 */
public class FontEditText extends EditText {
    public FontEditText(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");
        this.setTypeface(face);
    }

    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");
        this.setTypeface(face);
    }

    public FontEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/boahmed-alharf-bold.ttf");
        this.setTypeface(face);
    }
}
