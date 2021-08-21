package com.ashwin.android.mvirxjava.app.home;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ashwin.android.mvirxjava.Constant;
import com.ashwin.android.mvirxjava.app.BaseViewModel;
import com.ashwin.android.mvirxjava.app.Event;
import com.ashwin.android.mvirxjava.app.State;
import com.ashwin.android.mvirxjava.domain.model.Task;
import com.ashwin.android.mvirxjava.domain.repository.TaskRepository;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends BaseViewModel {
    private static final String SUB_TAG = HomeViewModel.class.getSimpleName();

    private TaskRepository mTaskRepository;
    private MutableLiveData<State<List<Task>>> mTasksLiveData;

    public HomeViewModel(TaskRepository taskRepository) {
        mTaskRepository = taskRepository;
        mTasksLiveData = new MutableLiveData<>(State.initial());
    }

    public LiveData<State<List<Task>>> getTasksLiveData() {
        return mTasksLiveData;
    }

    public void handleEvent(Event event) {
        switch (event.getType()) {
            case Event.GET_ALL: {
                Log.d(Constant.APP_TAG, SUB_TAG + ": event: GET_ALL");
                getAll();
                break;
            }
            default: {
                Log.d(Constant.APP_TAG, SUB_TAG + ": event: unknown");
            }
        }
    }

    private void getAll() {
        mTaskRepository.findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<Task>, State<List<Task>>>() {
                    @Override
                    public State<List<Task>> apply(@NonNull List<Task> tasks) throws Exception {
                        if (tasks != null) {
                            return State.success(tasks);
                        }
                        throw new RuntimeException();
                    }
                })
                .startWith(State.loading())
                .subscribeWith(new Observer<State<List<Task>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(Constant.APP_TAG, SUB_TAG + ": fetchTasks: onSubscribe (" + Thread.currentThread().getName() + ")");
                        autoDispose(d);
                    }

                    @Override
                    public void onNext(@NonNull State<List<Task>> state) {
                        Log.d(Constant.APP_TAG, SUB_TAG + ": fetchTasks: onNext (" + Thread.currentThread().getName() + ")");
                        mTasksLiveData.setValue(state);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(Constant.APP_TAG, SUB_TAG + ": fetchTasks: onError (" + Thread.currentThread().getName() + ")", e);
                        mTasksLiveData.setValue(State.error(e));
                    }

                    @Override
                    public void onComplete() {
                        Log.d(Constant.APP_TAG, SUB_TAG + ": fetchTasks: onComplete (" + Thread.currentThread().getName() + ")");
                    }
                });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mTaskRepository.close();
    }
}
