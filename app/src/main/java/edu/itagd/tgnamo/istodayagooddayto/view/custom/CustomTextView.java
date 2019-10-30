package edu.itagd.tgnamo.istodayagooddayto.view.custom;


import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;


public class CustomTextView extends TextView {

    /**
     * Custom Text View Constructor
     * @param context
     * @param attrs
     */
    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Custom "Overriden" setText method that will check
     * the current temperature and set the font based on the temperature
     * (on how cold or how hot it is)
     * @param text
     * @param type
     */
    public void setTempText(CharSequence text, BufferType type) {
        Typeface typeface = null;

        //Converts the CharSequence into an int for temperature comparison
        int temp = Integer.parseInt(text.toString());

        //If the temperature is hotter or equals to 16 degrees Celcius,
        //the font used will be hot.ttf
        if (temp >= 15){
            typeface = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/hot.ttf");
        }
        //else the font used will be cold.ttf
        else {
            typeface = Typeface.createFromAsset(getContext().getAssets(),
                    "fonts/cold.ttf");
        }

        setTypeface(typeface);
        super.setText(text, type);
    }

    /**
     * Custom "Overriden" setText method that will set the View Holder's title
     * with a specific font
     * @param text
     * @param type
     */
    public void setHeaderText(CharSequence text, BufferType type){
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/header.ttf");

        setTypeface(typeface);
        super.setText(text, type);
    }
}
