package com.ashwin.android.mvirxjava.data.repositoryimpl;

import android.util.Log;

import androidx.annotation.NonNull;

import com.ashwin.android.mvirxjava.Constant;
import com.ashwin.android.mvirxjava.data.source.local.LocalTaskSource;
import com.ashwin.android.mvirxjava.domain.model.Task;
import com.ashwin.android.mvirxjava.domain.repository.TaskRepository;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class TaskRepositoryImpl implements TaskRepository {
    private static final String SUB_TAG = TaskRepositoryImpl.class.getSimpleName();
    private LocalTaskSource mLocalTaskSource;

    public TaskRepositoryImpl(LocalTaskSource localTaskSource) {
        mLocalTaskSource = localTaskSource;
    }

    @Override
    public Observable<List<Task>> findAll() {
        return mLocalTaskSource.getTasks()
                .toObservable();
    }

    @Override
    public Observable<Task> findById(long id) {
        return mLocalTaskSource.getTasks()
                .toObservable()
                .observeOn(Schedulers.computation())
                .flatMap(new Function<List<Task>, Observable<Task>>() {
                    @Override
                    public Observable<Task> apply(@NonNull List<Task> tasks) throws Exception {
                        Log.d(Constant.APP_TAG, SUB_TAG + ": flatMap ( " + tasks.size() + " ) ( " + Thread.currentThread().getName() + " )");
                        return Observable.fromIterable(tasks);
                    }
                })
                .filter(new Predicate<Task>() {
                    @Override
                    public boolean test(@NonNull Task task) throws Exception {
                        Log.d(Constant.APP_TAG, SUB_TAG + ": filter ( " + task.getId() + " ) ( " + Thread.currentThread().getName() + " )");
                        return task.getId() == id;
                    }
                });
    }
}
