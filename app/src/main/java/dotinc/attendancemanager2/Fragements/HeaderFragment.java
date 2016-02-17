package dotinc.attendancemanager2.Fragements;


import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import dotinc.attendancemanager2.MainActivity;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.SubjectDatabase;
import dotinc.attendancemanager2.WeeklySubjectsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeaderFragment extends Fragment {

    private TextView timetable;
    private static TextView userName, overallPercTv;
    private ImageView userImage;
    //AttendanceDatabase attdb;
    ArrayList<SubjectsList> subjects;
    SubjectDatabase database;

    public HeaderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //attdb = new AttendanceDatabase(getActivity());
        View view = inflater.inflate(R.layout.fragment_header, container, false);
        timetable = (TextView) view.findViewById(R.id.time_table_btn);
        timetable.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        userName = (TextView) view.findViewById(R.id.name_text);
        userName.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Helper.JOSEFIN_SANS_BOLD));
        overallPercTv = (TextView) view.findViewById(R.id.overall_perc);
        overallPercTv.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Helper.JOSEFIN_SANS_REGULAR));
        userImage = (ImageView) view.findViewById(R.id.user_img);
        database = new SubjectDatabase(getActivity());
        subjects = new ArrayList<>();
        subjects = database.getAllSubjects();
        ((MainActivity)getActivity()).updateOverallPerc();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setUserImage(getActivity());
        setUserName(getActivity());
        //setOverallPerc(subjects);
        timetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WeeklySubjectsActivity.class);
                intent.putExtra("view_timetable", 1);
                startActivity(intent);
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

    public void setOverallPerc(double totalPresent, double totalClasses) {
        double overallPerc;
        if (totalClasses == 0 && totalPresent == 0) {
            overallPercTv.setText("No classes yet");
        } else {
            overallPerc = (totalPresent / totalClasses) * 100;
            overallPercTv.setText("Overall: " + String.format("%.1f", overallPerc) + "%");
        }

    }
}
