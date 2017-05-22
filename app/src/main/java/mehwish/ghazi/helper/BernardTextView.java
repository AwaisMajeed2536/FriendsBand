package mehwish.ghazi.helper;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;


public class BernardTextView extends AppCompatTextView {

    public BernardTextView(Context context) {
        super(context);
        setTypeface(context);
    }

    public BernardTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setTypeface(context);

    }

    public BernardTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(context);
    }

    private void setTypeface(Context context) {
        this.setTypeface(UtilHelpers.getBernardFont());
    }

}