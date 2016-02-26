package dotinc.attendancemanager2.Fragements;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.util.Attributes;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.TimeTableAdapter;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklySubjectsFragment extends Fragment {

    private RecyclerView recyclerView;
    private RelativeLayout emptyView;
    public RecyclerView.Adapter adapter;

    public TimeTableList timeTableList;
    public TimeTableDatabase database;
    private TextView emptyText;
    public ArrayList<TimeTableList> arrayList;
    public int pageNumber, view_timetable;
    private ImageView emptyImage;

    public WeeklySubjectsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_subjects, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.week_sub_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        emptyView = (RelativeLayout) view.findViewById(R.id.empty_view);
        emptyText = (TextView) view.findViewById(R.id.empty_text);
        emptyText.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Helper.OXYGEN_BOLD));
        emptyImage = (ImageView) view.findViewById(R.id.empty_image);
        Bundle bundle = getArguments();
        pageNumber = bundle.getInt("pageNumber", 1);
        view_timetable = bundle.getInt("view_timetable");
        if (view_timetable == 1) {
            emptyImage.setImageResource(R.drawable.rest_image);
            emptyText.setText("Enjoy your day off :)");
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        database = new TimeTableDatabase(getActivity());
        timeTableList = new TimeTableList();
        timeTableList.setDayCode(pageNumber);
        arrayList = database.getSubjects(timeTableList);
        adapter = new TimeTableAdapter(getActivity(), arrayList, timeTableList, WeeklySubjectsFragment.this, view_timetable, pageNumber);
        ((TimeTableAdapter) adapter).setMode(Attributes.Mode.Single);
        recyclerView.setAdapter(adapter);
        setEmptyView(arrayList.size());
    }

    public void setEmptyView(int size) {
        if (size == 0)
            emptyView.setVisibility(View.VISIBLE);
        else
            emptyView.setVisibility(View.GONE);
    }
}
