package mehwish.ghazi.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import mehwish.ghazi.ui.HomeActivity;

/**
 * Created by Tahir on 10/1/2016.
 */
public abstract class BaseFragment extends Fragment {

    public abstract String setFragmentTitle();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Spannable text = new SpannableString(setFragmentTitle());
        text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        ((HomeActivity)getActivity()).setTitle(text);
    }
}
