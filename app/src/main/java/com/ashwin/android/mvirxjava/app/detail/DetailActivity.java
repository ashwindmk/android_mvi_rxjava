package com.ashwin.android.mvirxjava.app.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.ashwin.android.mvirxjava.Constant;
import com.ashwin.android.mvirxjava.R;
import com.ashwin.android.mvirxjava.app.Event;
import com.ashwin.android.mvirxjava.app.State;
import com.ashwin.android.mvirxjava.app.di.TaskViewModelProvider;

public class DetailActivity extends AppCompatActivity {
    private static final String SUB_TAG = DetailActivity.class.getSimpleName();
    private DetailViewModel mDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mDetailViewModel = new ViewModelProvider(this, new TaskViewModelProvider()).get(DetailViewModel.class);

        mDetailViewModel.getTaskLiveData().observe(this, state -> {
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
                    Log.d(Constant.APP_TAG, SUB_TAG + ": State success: " + state.getData());
                    break;
                }
                case State.ERROR: {
                    Log.e(Constant.APP_TAG, SUB_TAG + ": State error", state.getError());
                    break;
                }
                default: {
                    Log.d(Constant.APP_TAG, SUB_TAG + ": State unknown");
                }
            }
        });

        Button getOneButton = findViewById(R.id.get_one_button);
        getOneButton.setOnClickListener(v -> {
            getOne();
        });
    }

    private void getOne() {
        mDetailViewModel.handleEvent(Event.getEvent(Event.GET_ONE, 1L));
    }
}
