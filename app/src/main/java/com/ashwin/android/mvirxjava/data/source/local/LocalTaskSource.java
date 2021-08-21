package com.ashwin.android.mvirxjava.data.source.local;

import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ashwin.android.mvirxjava.Constant;
import com.ashwin.android.mvirxjava.domain.model.Task;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;

public class LocalTaskSource {
    private static final String SUB_TAG = LocalTaskSource.class.getSimpleName();

    public Single<List<Task>> getTasks() {
        return Single.create(new SingleOnSubscribe<List<Task>>() {
            @Override
            public void subscribe(@NonNull SingleEmitter<List<Task>> emitter) throws Exception {
                Log.d(Constant.APP_TAG, SUB_TAG + ": getTasks: subscribe (" + Thread.currentThread().getName() + ")");
                try {
                    SystemClock.sleep(4000);
                    List<Task> tasks = new ArrayList<>();
                    tasks.add(new Task(1L, "Task 1", 1, false));
                    tasks.add(new Task(2L, "Task 2", 2, true));
                    tasks.add(new Task(3L, "Task 3", 3, false));
                    tasks.add(new Task(4L, "Task 4", 4, true));
                    tasks.add(new Task(5L, "Task 5", 1, false));
                    tasks.add(new Task(6L, "Task 6", 3, false));
                    tasks.add(new Task(7L, "Task 7", 2, false));
                    emitter.onSuccess(tasks);
                } catch (Exception e) {
                    // Any error will directly go to the observer's onError without going to any of the operators.
                    emitter.onError(e);
                }
            }
        });
    }
}
