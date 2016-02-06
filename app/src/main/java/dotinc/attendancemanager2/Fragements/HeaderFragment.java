package dotinc.attendancemanager2.Fragements;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import dotinc.attendancemanager2.MainActivity;
import dotinc.attendancemanager2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeaderFragment extends Fragment {

    private ImageButton detailedAnalysis;
    public HeaderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_header, container, false);

        detailedAnalysis= (ImageButton) view.findViewById(R.id.det_anl_btn);


        detailedAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).detialedAnalysisShow();
            }
        });
        return view;
    }

}
