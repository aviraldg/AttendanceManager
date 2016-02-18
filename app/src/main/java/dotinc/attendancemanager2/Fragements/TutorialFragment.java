package dotinc.attendancemanager2.Fragements;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import dotinc.attendancemanager2.MainActivity;
import dotinc.attendancemanager2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TutorialFragment extends Fragment {

    private ImageView imageView;
    private Button next, prev;
    private int number = 0;


    public TutorialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tutorial, container, false);
        imageView = (ImageView) view.findViewById(R.id.image);
        next = (Button) view.findViewById(R.id.next_btn);
        prev = (Button) view.findViewById(R.id.prev_btn);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number++;
                switch (number) {
                    case 1:
                        prev.setVisibility(View.VISIBLE);
                        imageView.setImageResource(R.drawable.tutorial2);
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.tutorial3);
                        break;
                    case 3:
                        imageView.setImageResource(R.drawable.tutorial4);
                        next.setText("Got it!");
                        break;
                    case 4:
                        getActivity().getSupportFragmentManager().beginTransaction().remove(TutorialFragment.this).commit();
                        ((MainActivity) getActivity()).closeTutorial();
                        break;
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number--;
                switch (number) {
                    case 1:
                        imageView.setImageResource(R.drawable.tutorial2);
                        next.setText("Next");
                        break;
                    case 2:
                        imageView.setImageResource(R.drawable.tutorial3);
                        next.setText("Next");
                        break;
                    case 0:
                        prev.setVisibility(View.GONE);
                        imageView.setImageResource(R.drawable.tutorial1);
                        break;
                }
            }
        });


    }
}
