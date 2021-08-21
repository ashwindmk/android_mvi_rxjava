package com.ashwin.android.mvirxjava.app.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.ashwin.android.mvirxjava.Constant;
import com.ashwin.android.mvirxjava.R;
import com.ashwin.android.mvirxjava.app.Event;
import com.ashwin.android.mvirxjava.app.State;
import com.ashwin.android.mvirxjava.app.detail.DetailActivity;
import com.ashwin.android.mvirxjava.app.di.TaskViewModelProvider;

public class HomeActivity extends AppCompatActivity {
    private static final String SUB_TAG = HomeActivity.class.getSimpleName();
    private HomeViewModel mHomeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mHomeViewModel = new ViewModelProvider(this, new TaskViewModelProvider()).get(HomeViewModel.class);

        mHomeViewModel.getTasksLiveData().observe(this, state -> {
            switch (state.getType()) {
                case State.INITIAL: {
                    Log.d(Constant.APP_TAG, SUB_TAG + ": State initial");
                    break;
                }
                case State.LOADING: {
                    Log.d(Constant.APP_TAG, SUB_TAG + ": State loading");
                    break;
                }
                case State.SUCCESS: {
                    Log.d(Constant.APP_TAG, SUB_TAG + ": State success: " + state.getData().size());
                    break;
                }
                case State.ERROR: {
                    Log.d(Constant.APP_TAG, SUB_TAG + ": State error: " + state.getError());
                    break;
                }
                default: {
                    Log.d(Constant.APP_TAG, SUB_TAG + ": Unknown state");
                    break;
                }
            }
        });

        Button getAllButton = findViewById(R.id.get_all_button);
        getAllButton.setOnClickListener(v -> {
            getAll();
        });

        Button openDetailButton = findViewById(R.id.open_detail_button);
        openDetailButton.setOnClickListener(v -> {
            openDetail();
        });
    }

    private void getAll() {
        mHomeViewModel.handleEvent(Event.getEvent(Event.GET_ALL, null));
    }

    private void openDetail() {
        startActivity(new Intent(this, DetailActivity.class));
    }
}