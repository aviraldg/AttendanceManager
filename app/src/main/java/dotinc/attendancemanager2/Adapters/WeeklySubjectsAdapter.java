package dotinc.attendancemanager2.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import dotinc.attendancemanager2.Fragements.WeeklySubjectsFragment;

/**
 * Created by ddvlslyr on 24/1/16.
 */
public class WeeklySubjectsAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;
    private String[] tabTitles;

    public WeeklySubjectsAdapter(FragmentManager fm, ArrayList<Fragment> fragments, String[] tabTitles) {
        super(fm);
        this.fragments = fragments;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        WeeklySubjectsFragment fragment = (WeeklySubjectsFragment) fragments.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("pageNumber", position + 1);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
