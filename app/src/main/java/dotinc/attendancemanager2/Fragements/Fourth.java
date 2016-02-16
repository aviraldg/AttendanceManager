package dotinc.attendancemanager2.Fragements;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dotinc.attendancemanager2.ChooseAvatarActivity;
import dotinc.attendancemanager2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fourth extends Fragment {

    private Button start;

    public Fourth() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth, container, false);
        start = (Button) view.findViewById(R.id.start_button);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChooseAvatarActivity.class));
            }
        });
    }
}
