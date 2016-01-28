package dotinc.attendancemanager2.Fragements;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.WeeklySubjectsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
    private ImageButton viewTimeTable;

    public SecondFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        viewTimeTable = (ImageButton) view.findViewById(R.id.view_timetable);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewTimeTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WeeklySubjectsActivity.class);
                startActivity(intent);
            }
        });
    }
}
