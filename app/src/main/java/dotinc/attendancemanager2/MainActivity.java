package dotinc.attendancemanager2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import dotinc.attendancemanager2.Adapters.AttendanceAdapter;
import dotinc.attendancemanager2.Adapters.MainViewPagerAdapter;
import dotinc.attendancemanager2.Fragements.HeaderFragment;
import dotinc.attendancemanager2.Fragements.SecondFragment;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Objects.TimeTableList;
import dotinc.attendancemanager2.Utils.AttendanceDatabase;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.ProgressPageIndicator;
import dotinc.attendancemanager2.Utils.SubjectDatabase;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private RecyclerView recyclerView, exclRecyclerView;
    private CoordinatorLayout root;
    private RelativeLayout extraClassLayout;
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ViewPager pager;
    private ArrayList<Fragment> pageList;
    private HeaderFragment headerFragment;
    private ProgressPageIndicator indicator;
    private FloatingActionButton fab;

    private ArrayList<TimeTableList> allSubjectsArrayList;      //add
    private ArrayList<TimeTableList> arrayList;
    private ArrayList<SubjectsList> subjectsName;
    private ArrayList<String> subjects;
    private int dayCode;


    private AttendanceDatabase database;
    private TimeTableDatabase timeTableDatabase;
    private TimeTableList timeTableList;
    private SubjectDatabase subjectDatabase;                            //add
    private String day;
    private Date date;
    private String activityName;
    private AttendanceAdapter exadapter;
    private AttendanceAdapter mainadapter;
    private Boolean exclViewOpen = false, attAllViewOpen = false;


    void instantiate() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = MainActivity.this;

        extraClassLayout = (RelativeLayout) findViewById(R.id.extra_class_layout);
        activityName = "MainActivity";

        date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        day = format.format(date);


        root = (CoordinatorLayout) findViewById(R.id.root);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        pager = (ViewPager) findViewById(R.id.pager);
        indicator = (ProgressPageIndicator) findViewById(R.id.pageIndicator);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        exclRecyclerView = (RecyclerView) findViewById(R.id.extra_class_recycler_view);
        exclRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        exclRecyclerView.setHasFixedSize(true);

        subjectsName = new ArrayList<>();
        subjects = new ArrayList<>();
        pageList = new ArrayList<>();
        headerFragment = new HeaderFragment();
        pageList.add(headerFragment);
        pageList.add(new SecondFragment());
        allSubjectsArrayList = new ArrayList<>();

        timeTableList = new TimeTableList();
        database = new AttendanceDatabase(this);
        timeTableDatabase = new TimeTableDatabase(this);
        subjectDatabase = new SubjectDatabase(this);

        dayCode = getdaycode();
        timeTableList.setDayCode(dayCode);
        arrayList = timeTableDatabase.getSubjects(timeTableList);

    }

    public void showSnackbar(String meesage) {
        Snackbar.make(root, meesage, Snackbar.LENGTH_SHORT).show();
    }

    private void extraClass() {
        timeTableList.setDayCode(dayCode);                //daycode
        arrayList = timeTableDatabase.getSubjects(timeTableList);
        allSubjectsArrayList = subjectDatabase.getAllSubjectsForExtra();
        for (int i = 0; i < arrayList.size(); i++) {
            for (int j = 0; j < allSubjectsArrayList.size(); j++) {
                if ((allSubjectsArrayList.get(j).getSubjectName().equals(arrayList.get(i).getSubjectName())))
                    allSubjectsArrayList.remove(j);
            }
        }
        exadapter = new AttendanceAdapter(this, allSubjectsArrayList, day, activityName);
        exclRecyclerView.setAdapter(exadapter);
    }

    private void setTitle(String dayName) {
        getSupportActionBar().setTitle(dayName);
    }

    private int getdaycode() {
        int day_code = 1;
        Date date = new Date();
        String myDate;
        SimpleDateFormat format = new SimpleDateFormat("EEE");
        myDate = format.format(date.getTime());
        switch (myDate) {
            case "Mon":
                day_code = 1;
                setTitle("Monday");
                break;
            case "Tue":
                day_code = 2;
                setTitle("Tuesday");
                break;
            case "Wed":
                day_code = 3;
                setTitle("Wednesday");
                break;
            case "Thu":
                day_code = 4;
                setTitle("Thursday");
                break;
            case "Fri":
                setTitle("Friday");
                day_code = 5;
                break;
            case "Sat":
                day_code = 6;
                setTitle("Saturday");
                break;
            case "Sun":
                setTitle("Sunday");
                break;
        }
        return day_code;
    }

    public void updateOverallPerc() {
        headerFragment.setOverallPerc();
    }

    @Override
    protected void onResume() {
        super.onResume();
        exadapter.notifyDataSetChanged();
        mainadapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instantiate();
        extraClass();
        mainadapter = new AttendanceAdapter(this, arrayList, day, activityName);
        recyclerView.setAdapter(mainadapter);
        pager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(), pageList));

        pager.addOnPageChangeListener(new CustomOnPageChangeListener());
        indicator.setViewPager(pager);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    showExtraClass();
                } else {
                    //------------code for pre-lolipop for extra class------------//
                }
            }

        });


        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {
                if (!exclViewOpen) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        if (!attAllViewOpen)
                            markAllClass();
                    } else {
                        //------code for pre-lolipop-------//

                    }
                }
                return true;
            }
        });
    }

    public void attendAll(View view) {
        //**************** Define the functionality here ***********//
        database.addAllAttendance(arrayList, 1, day);
        mainadapter.notifyDataSetChanged();
        markedAtt();
    }

    public void bunkedAll(View view) {
        //**************** Define the functionality here ***********//
        database.addAllAttendance(arrayList, 0, day);
        mainadapter.notifyDataSetChanged();
        markedAtt();
    }

    public void noClassAll(View view) {
        //**************** Define the functionality here ***********//
        database.addAllAttendance(arrayList, -1, day);
        mainadapter.notifyDataSetChanged();
        markedAtt();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void markAllClass() {

        final View fullAttView = findViewById(R.id.full_att_layout);
        Animator anim = null;
        attAllViewOpen = true;

        int cX = fullAttView.getWidth();
        int cY = 0;
        int finalRadius = Math.max(fullAttView.getWidth(), fullAttView.getHeight() + 1000);

        anim = ViewAnimationUtils.createCircularReveal(fullAttView, cX, cY, 0, finalRadius);
        anim.setDuration(500).setInterpolator(new DecelerateInterpolator(1));
        fullAttView.setVisibility(View.VISIBLE);
        anim.start();
        fab.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab.setImageResource(R.mipmap.ic_clear_white_36dp);
                fab.show();
            }
        }, 300);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void markedAtt() {
        final View fullAttView = findViewById(R.id.full_att_layout);

        Animator anim = null;
        attAllViewOpen = false;

        int cX = fullAttView.getWidth();
        int cY = 0;
        int maxRadius = 0;

        anim = ViewAnimationUtils.createCircularReveal(fullAttView, cX, cY, fullAttView.getWidth(), maxRadius);
        anim.setDuration(500).setInterpolator(new DecelerateInterpolator(1));
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fullAttView.setVisibility(View.GONE);
            }
        });
        anim.start();
        fab.hide();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fab.setImageResource(R.mipmap.ic_add_white_36dp);
                fab.show();
            }
        }, 300);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showExtraClass() {
        final View extraView = findViewById(R.id.extra_class_layout);
        Animator anim = null;
        int cx = extraView.getWidth();
        int cY = 0;
        if (!attAllViewOpen) {
            if (!exclViewOpen) {
                int finalRadius = Math.max(extraView.getWidth(), extraView.getHeight() + 1000);

                anim = ViewAnimationUtils.createCircularReveal(extraView, cx, cY, 0, finalRadius);
                anim.setDuration(1000).setInterpolator(new DecelerateInterpolator(1));
                exclViewOpen = true;
                extraView.setVisibility(View.VISIBLE);
                anim.start();
                fab.hide();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fab.setImageResource(R.mipmap.ic_done_white_36dp);
                        fab.show();
                    }
                }, 300);

            } else {
                int finalRadius = 0;
                anim = ViewAnimationUtils.createCircularReveal(extraView,
                        cx, cY, extraView.getHeight() + 1000, finalRadius);
                anim.setDuration(500).setInterpolator(new DecelerateInterpolator(1));
                anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        extraView.setVisibility(View.INVISIBLE);

                    }
                });
                anim.start();
                fab.hide();
                exclViewOpen = false;

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fab.setImageResource(R.mipmap.ic_add_white_36dp);
                        fab.show();
                    }
                }, 300);
            }
        } else {
            markedAtt();
        }
    }

    @Override
    public void onBackPressed() {
        if (exclViewOpen) {
            final View extraView = findViewById(R.id.extra_class_layout);
            Animator anim = null;
            int cx = extraView.getWidth();
            int cY = 0;
            int finalRadius = 0;
            anim = ViewAnimationUtils.createCircularReveal(extraView,
                    cx, cY, extraView.getHeight() + 1000, finalRadius);
            anim.setDuration(500).setInterpolator(new DecelerateInterpolator(1));
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    extraView.setVisibility(View.INVISIBLE);

                }
            });
            anim.start();
            fab.hide();
            exclViewOpen = false;

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    fab.setImageResource(R.mipmap.ic_add_white_36dp);
                    fab.show();
                }
            }, 300);
        } else if (attAllViewOpen) {
            markedAtt();
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_page_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.clear:
                Helper.clearData(context);
                break;
            case R.id.settings:
                startActivity(new Intent(context, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CustomOnPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    indicator.setViewPager(pager, 0);
                    indicator.setFillColor(getResources().getColor(android.R.color.white));
                    break;
                case 1:
                    indicator.setViewPager(pager, 1);
                    indicator.setFillColor(getResources().getColor(android.R.color.white));
                    break;
                default:
                    break;
            }
            super.onPageSelected(position);
        }
    }
}
