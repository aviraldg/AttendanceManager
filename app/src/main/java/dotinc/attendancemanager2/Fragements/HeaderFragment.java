package dotinc.attendancemanager2.Fragements;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.DetailedAnalysisActivity;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.SubjectDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeaderFragment extends Fragment {

    private ImageButton detailedAnalysis;
    private TextView userName, overallPercTv;
    private ImageView userImage;

    public HeaderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);
        detailedAnalysis = (ImageButton) view.findViewById(R.id.det_anl_btn);
        userName = (TextView) view.findViewById(R.id.name_text);
        overallPercTv = (TextView) view.findViewById(R.id.overall_perc);
        userImage = (ImageView) view.findViewById(R.id.user_img);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setUserImage(getActivity());
        setUserName(getActivity());
        setOverallPerc();
        detailedAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DetailedAnalysisActivity.class));
            }
        });
    }


    private void setUserName(Context context) {
        userName.setText(Helper.getFromPref(context, Helper.USER_NAME, ""));
    }

    private void setUserImage(Context context) {
        int userImageId = Integer.parseInt(Helper.getFromPref(context, Helper.USER_IMAGE_ID, String.valueOf(0)));

        if (userImageId == 1)
            userImage.setImageResource(R.drawable.user_male);
        else if (userImageId == 2)
            userImage.setImageResource(R.drawable.user_female);
    }

    public void setOverallPerc() {
        double totalPresent = 0, totalClasses = 0;
        double overallPerc;
        SubjectDatabase db = new SubjectDatabase(getActivity());
        AttendanceDatabase attdb = new AttendanceDatabase(getActivity());
        ArrayList<SubjectsList> subjectsLists = db.getAllSubjects();

        for (int pos = 0; pos < subjectsLists.size(); pos++) {
            int id = subjectsLists.get(pos).getId();
            totalPresent += attdb.totalPresent(id);
            totalClasses += attdb.totalClasses(id);
        }

        if (attdb.checkEmpty())
            overallPercTv.setText(getResources().getString(R.string.noClasses));
        else {
            overallPerc = (totalPresent / totalClasses) * 100;
            overallPercTv.setText("Overall: " + String.format("%.1f", overallPerc) + "%");
        }

        db.close();
        attdb.close();
    }
}
