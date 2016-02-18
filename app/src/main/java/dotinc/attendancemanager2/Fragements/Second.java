package dotinc.attendancemanager2.Fragements;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Second extends Fragment {

    private TextView textView;
    private String[] titles;

    public Second() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second2, container, false);
        textView = (TextView) view.findViewById(R.id.title_text);
        textView.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Helper.OXYGEN_BOLD));
        titles = getActivity().getResources().getStringArray(R.array.viewpager_title);
        textView.setText(titles[1]);
        return view;
    }
}
