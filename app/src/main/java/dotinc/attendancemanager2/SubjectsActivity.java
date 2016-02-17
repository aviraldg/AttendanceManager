package dotinc.attendancemanager2;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import dotinc.attendancemanager2.Adapters.SubjectsAdapter;
import dotinc.attendancemanager2.Objects.SubjectsList;
import dotinc.attendancemanager2.Utils.Helper;
import dotinc.attendancemanager2.Utils.SubjectDatabase;
import dotinc.attendancemanager2.Utils.TimeTableDatabase;

public class SubjectsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton addSubjects;
    private TextView subjectText, swipeHelpText;
    private TextView multHelpText, multSubtitle;
    private LinearLayout edittextLayout, helpText;
    private View view1;
    private ImageView done, removeImage;
    private ArrayList<SubjectsList> arrayList;
    private EditText subject;
    private LinearLayout emptyView;
    private CoordinatorLayout root;
    private SubjectsAdapter adapter;
    private SubjectDatabase database;
    private SubjectsList subjectsList;
    private TimeTableDatabase timeTableDatabase;
    private Typeface oxyBold, josefinBold, josefinReg;

    private ArrayList<EditText> editTexts;
    private ArrayList<String> subjectName, prevSubjects;
    private boolean isVisible, fromSettings;

    private void instantiate() {

        oxyBold = Typeface.createFromAsset(getAssets(), Helper.OXYGEN_BOLD);
        josefinBold = Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_BOLD);
        josefinReg = Typeface.createFromAsset(getAssets(), Helper.JOSEFIN_SANS_REGULAR);

        addSubjects = (FloatingActionButton) findViewById(R.id.add_subjects);
        addSubjects.hide();
        subjectText = (TextView) findViewById(R.id.subject_layout_title);
        subjectText.setTypeface(oxyBold);
        swipeHelpText = (TextView) findViewById(R.id.swipe_help_text);
        swipeHelpText.setTypeface(josefinReg);
        helpText = (LinearLayout) findViewById(R.id.help_text);
        view1 = findViewById(R.id.view1);

        multSubtitle = (TextView) findViewById(R.id.titleView);
        multSubtitle.setTypeface(oxyBold);
        multHelpText = (TextView) findViewById(R.id.mul_sub_help);
        multHelpText.setTypeface(josefinReg);

        fromSettings = getIntent().getBooleanExtra("Settings", false);
        database = new SubjectDatabase(this);
        timeTableDatabase = new TimeTableDatabase(this);
        emptyView = (LinearLayout) findViewById(R.id.empty_view);

        edittextLayout = (LinearLayout) findViewById(R.id.subjects_view);
        editTexts = new ArrayList<>();
        subjectName = new ArrayList<>();
        prevSubjects = new ArrayList<>();
        root = (CoordinatorLayout) findViewById(R.id.root);
        done = (ImageView) findViewById(R.id.done);
        removeImage = (ImageView) findViewById(R.id.removeEdit);
        subjectsList = new SubjectsList();
        arrayList = database.getAllSubjects();
        recyclerView = (RecyclerView) findViewById(R.id.subjects);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubjectsAdapter(this, arrayList);
        setEmptyView(arrayList.size());
        getPreviousSubjects();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);

        instantiate();

        recyclerView.setAdapter(adapter);

        addSubjects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    addMultipleSubjects();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(SubjectsActivity.this);
                    LayoutInflater inflater = getLayoutInflater();
                    View view = inflater.inflate(R.layout.add_subject, null);
                    subject = (EditText) view.findViewById(R.id.subject_name);
                    subject.setTypeface(josefinBold);

                    dialog.setView(view);
                    dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String subjectName = subject.getText().toString().trim();
                            if (checkIfAlreadyEntered(subjectName) == 0 && subjectName.length() != 0) {
                                subjectsList.setSubjectName(subjectName);
                                database.addsubject(subjectsList);
                                arrayList.clear();
                                arrayList.addAll(database.getAllSubjects());
                                setEmptyView(arrayList.size());
                                adapter.notifyDataSetChanged();
                            } else if (subjectName.length() == 0)
                                showSnackbar("Subject cannot be empty");
                            else
                                showSnackbar("Subject already entered");
                        }
                    });
                    dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    dialog.create().show();
                }
            }
        });
    }

    private void addMultipleSubjects() {
        final View addSubjectslayout = findViewById(R.id.add_multiple_subjects);
        Animator anim = null;

        int cX = addSubjectslayout.getLeft() + addSubjectslayout.getRight();
        int cY = addSubjectslayout.getBottom();

        if (addSubjectslayout.getVisibility() == View.INVISIBLE) {
            int finalRadius = Math.max(cX, cY + 1000);
            anim = ViewAnimationUtils.createCircularReveal(addSubjectslayout, cX, cY, 0, finalRadius);
            anim.setDuration(500).setInterpolator(new DecelerateInterpolator());
            addSubjectslayout.setVisibility(View.VISIBLE);
            anim.addListener(new AnimatorListenerAdapter() {

                @Override
                public void onAnimationStart(Animator animation) {
                    super.onAnimationStart(animation);
                    editTexts.clear();
                    edittextLayout.removeAllViews();
                    done.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    addEditText(addSubjectslayout);
                    helpText.setVisibility(View.VISIBLE);
                    addSubjects.setImageResource(R.mipmap.ic_done_white_36dp);
                    addSubjects.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
                    addSubjects.setRippleColor(getResources().getColor(R.color.colorPrimary));
                    addSubjects.setImageTintList(getResources().getColorStateList(R.color.white));
                    addSubjects.show();
                    isVisible = true;
                }
            });
            anim.start();
            addSubjects.hide();

        } else if (addSubjectslayout.getVisibility() == View.VISIBLE) {
            if (editTexts.isEmpty()) {
                hideAddSubjectsView();
            } else {
                getMultipleSubjects();
                if (checkDuplicate() == false)
                    showSnackbar("Remove subjects with same name");
                else if (!editTexts.isEmpty() && checkEmptyString()) {
                    showSnackbar("Subject name cannot be blank");
                } else {
                    database.addMultipleSubjects(subjectName);

                    int finalRadius = 0;
                    anim = ViewAnimationUtils.createCircularReveal(addSubjectslayout,
                            cX, cY, addSubjectslayout.getHeight() + 1000, finalRadius);
                    anim.setDuration(500).setInterpolator(new DecelerateInterpolator());
                    anim.addListener(new AnimatorListenerAdapter() {

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            InputMethodManager imn = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imn.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                            addSubjectslayout.setVisibility(View.INVISIBLE);
                            addSubjects.setImageResource(R.mipmap.ic_add_white_36dp);
                            addSubjects.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
                            addSubjects.setRippleColor(getResources().getColor(R.color.colorPrimary));
                            addSubjects.setImageTintList(getResources().getColorStateList(R.color.white));
                            addSubjects.show();
                            isVisible = false;
                            arrayList.clear();
                            setEmptyView(arrayList.size());
                            arrayList.addAll(database.getAllSubjects());
                            setEmptyView(arrayList.size());
                            adapter.notifyDataSetChanged();
                        }
                    });
                    anim.start();
                    addSubjects.hide();

                }
            }
        }
    }

    private void getPreviousSubjects() {
        for (int pos = 0; pos < arrayList.size(); pos++)
            prevSubjects.add(arrayList.get(pos).getSubjectName());
    }

    public void addEditText(View view) {
        EditText ed = new EditText(this);
        ed.setId(editTexts.size() + 1);
        ed.setHint("Enter subject " + (editTexts.size() + 1));
        ed.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        ed.setPadding(25, 25, 25, 25);
        ed.setSingleLine(true);
        ed.setBackground(getResources().getDrawable(R.drawable.item_border));
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(16, 15, 16, 15);
        ed.setLayoutParams(layoutParams);
        ed.requestFocus();
        ed.setTypeface(josefinBold);
        edittextLayout.addView(ed);
        editTexts.add(ed);
        if (removeImage.getVisibility() == View.GONE)
            removeImage.setVisibility(View.VISIBLE);
    }

    public void showHelp(View view) {
        if (helpText.getVisibility() == View.GONE)
            helpText.setVisibility(View.VISIBLE);
        else
            helpText.setVisibility(View.GONE);
    }

    public void removeEditText(View view) {
        edittextLayout.removeViewAt(editTexts.size() - 1);
        editTexts.remove(editTexts.size() - 1);
        if (!subjectName.isEmpty())
            subjectName.remove(subjectName.size() - 1);
        if (editTexts.size() == 0)
            removeImage.setVisibility(View.GONE);
    }

    public int checkIfAlreadyEntered(String subject) {
        int flag = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getSubjectName().equals(subject)) {
                flag = 1;
                break;
            }
        }
        return flag;
    }


    public void setEmptyView(int size) {
        if (size == 0) {
            emptyView.setVisibility(View.VISIBLE);
            subjectText.setVisibility(View.GONE);
            view1.setVisibility(View.GONE);
            done.setVisibility(View.GONE);
            swipeHelpText.setVisibility(View.GONE);
            recyclerView.setVisibility(View.GONE);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addSubjects.setImageResource(R.mipmap.ic_add_white_36dp);
                addSubjects.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                addSubjects.setRippleColor(getResources().getColor(R.color.backgroundColor));
                addSubjects.setImageTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addSubjects.show();
                    }
                }, 500);
            }
        } else {
            emptyView.setVisibility(View.GONE);
            subjectText.setVisibility(View.VISIBLE);
            view1.setVisibility(View.VISIBLE);
            done.setVisibility(View.VISIBLE);
            swipeHelpText.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                addSubjects.setImageResource(R.mipmap.ic_add_white_36dp);
                addSubjects.setBackgroundTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
                addSubjects.setRippleColor(getResources().getColor(R.color.colorPrimary));
                addSubjects.setImageTintList(getResources().getColorStateList(R.color.white));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addSubjects.show();
                    }
                }, 500);
            }
        }

    }

    public void showSnackbar(String message) {
        Snackbar.make(root, message, Snackbar.LENGTH_LONG).show();
    }

    public void doneAddSubjects(View view) {
        if (fromSettings)
            finish();
        else
            startActivity(new Intent(SubjectsActivity.this, WeeklySubjectsActivity.class));
    }


    private void getMultipleSubjects() {
        subjectName.clear();
        if (!editTexts.isEmpty()) {
            for (int size = 0; size < editTexts.size(); size++)
                subjectName.add(editTexts.get(size).getText().toString().trim());
        }
    }

    private boolean checkDuplicate() {
        ArrayList<String> subjects = new ArrayList<>();
        prevSubjects.clear();
        getPreviousSubjects();
        subjects.addAll(prevSubjects);
        subjects.addAll(subjectName);
        Set<String> names = new HashSet<>(prevSubjects);
        names.addAll(subjectName);
        if (names.size() == subjects.size())
            return true;
        else
            return false;
    }

    private boolean checkEmptyString() {
        for (EditText editText : editTexts)
            if (editText.getText().toString().trim().matches(""))
                return true;
        return false;
    }

    private void hideAddSubjectsView() {
        final View addSubjectslayout = findViewById(R.id.add_multiple_subjects);
        Animator anim = null;

        int cX = addSubjectslayout.getLeft() + addSubjectslayout.getRight();
        int cY = addSubjectslayout.getBottom();

        int finalRadius = 0;
        anim = ViewAnimationUtils.createCircularReveal(addSubjectslayout,
                cX, cY, addSubjectslayout.getHeight() + 1000, finalRadius);
        anim.setDuration(500).setInterpolator(new DecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                addSubjectslayout.setVisibility(View.INVISIBLE);
                addSubjects.setImageResource(R.mipmap.ic_add_white_36dp);
                addSubjects.setBackgroundTintList(getResources().getColorStateList(R.color.white));
                addSubjects.setRippleColor(getResources().getColor(R.color.backgroundColor));
                addSubjects.setImageTintList(getResources().getColorStateList(R.color.colorPrimaryDark));
                addSubjects.show();
                setEmptyView(arrayList.size());
                isVisible = false;
            }
        });
        anim.start();
        addSubjects.hide();
    }

    @Override
    public void onBackPressed() {
        if (isVisible) {
            hideAddSubjectsView();
        } else
            super.onBackPressed();
    }
}
