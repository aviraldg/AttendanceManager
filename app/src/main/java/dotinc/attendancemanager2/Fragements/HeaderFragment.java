package dotinc.attendancemanager2.Fragements;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import dotinc.attendancemanager2.MainActivity;
import dotinc.attendancemanager2.R;
import dotinc.attendancemanager2.Utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeaderFragment extends Fragment {

    private ImageButton detailedAnalysis;
    private TextView userName;
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
        userImage = (ImageView) view.findViewById(R.id.user_img);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        setUserImage(getActivity());
        setUserName(getActivity());
        detailedAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).detialedAnalysisShow();
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
}
