package dotinc.attendancemanager2.Fragements;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import dotinc.attendancemanager2.Adapters.TimeTableAdapter;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeeklySubjectsFragment extends Fragment {

    private RecyclerView recyclerView;
    public static TimeTableAdapter adapter;
    public static int pageNumber;
    public static TimeTableList timeTableList;
    public static TimeTableDatabase database;
    public static ArrayList<TimeTableList> arrayList;


    public WeeklySubjectsFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weekly_subjects, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.week_sub_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        Bundle bundle = getArguments();
        pageNumber = bundle.getInt("pageNumber", 1);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d("option_c","inside");
        database = new TimeTableDatabase(getActivity());
        timeTableList = new TimeTableList();
        timeTableList.setDayCode(pageNumber);
        arrayList = database.getSubjects(timeTableList);
        Log.d("option_size_1", String.valueOf(arrayList.size()));
        adapter = new TimeTableAdapter(getActivity(), arrayList, timeTableList, WeeklySubjectsFragment.this);
        recyclerView.setAdapter(adapter);
    }

    public static void updateView() {
        arrayList.clear();
        timeTableList.setDayCode(pageNumber);
        arrayList.addAll(database.getSubjects(timeTableList));
        adapter.notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        database.toast();
        Log.d("option_po", String.valueOf(position));
        Log.d("option_po", arrayList.get(position).getSubjectName());
        Log.d("option_po", String.valueOf(arrayList.get(position).getPosition()));
        Log.d("option_po", String.valueOf(arrayList.get(position).getDayCode()));
        timeTableList.setSubjectName(arrayList.get(position).getSubjectName());
        timeTableList.setId(arrayList.get(position).getId());
        timeTableList.setPosition(arrayList.get(position).getPosition());
        database.deleteTimeTable(timeTableList);

        arrayList.clear();
        timeTableList.setDayCode(pageNumber);
        arrayList.addAll(database.getSubjects(timeTableList));
        adapter.notifyDataSetChanged();
        database.toast();
    }
}
