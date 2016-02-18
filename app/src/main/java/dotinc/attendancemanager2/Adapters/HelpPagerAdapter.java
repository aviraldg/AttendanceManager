package dotinc.attendancemanager2.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import dotinc.attendancemanager2.Fragements.TutorialFragment;

/**
 * Created by ddvlslyr on 18/2/16.
 */
public class HelpPagerAdapter extends FragmentStatePagerAdapter {

    public HelpPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        TutorialFragment fragment = new TutorialFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("PageNumber", position);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
