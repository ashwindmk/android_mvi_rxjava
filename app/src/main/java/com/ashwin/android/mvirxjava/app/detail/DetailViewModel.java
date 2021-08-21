package com.ashwin.android.mvirxjava.app.detail;

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

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class DetailViewModel extends BaseViewModel {
    private static final String SUB_TAG = DetailViewModel.class.getSimpleName();
    private TaskRepository mTaskRepository;
    private MutableLiveData<State<Task>> mTaskLiveData;

    public DetailViewModel(TaskRepository taskRepository) {
        mTaskRepository = taskRepository;
        mTaskLiveData = new MutableLiveData<>(State.initial());
    }

    public LiveData<State<Task>> getTaskLiveData() {
        return mTaskLiveData;
    }

    public void handleEvent(Event event) {
        switch (event.getType()) {
            case Event.GET_ONE: {
                Log.d(Constant.APP_TAG, SUB_TAG + ": event: GET_ONE ( " + event.getData() + " ) (" + Thread.currentThread().getName() + ")");
                loadTask((long) event.getData());
                break;
            }
            default: {
                Log.d(Constant.APP_TAG, SUB_TAG + ": event: unknown event: " + event.getType() + " (" + Thread.currentThread().getName() + ")");
            }
        }
    }

    public void loadTask(long id) {
        mTaskRepository.findById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Task, State<Task>>() {
                    @Override
                    public State<Task> apply(@NonNull Task task) throws Exception {
                        if (task != null) {
                            return State.success(task);
                        }
                        throw new IllegalArgumentException("Task not found");
                    }
                })
                .startWith(State.loading())
                .subscribeWith(new Observer<State<Task>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(Constant.APP_TAG, SUB_TAG + ": fetchTasks: onSubscribe (" + Thread.currentThread().getName() + ")");
                        autoDispose(d);
                    }

                    @Override
                    public void onNext(@NonNull State<Task> state) {
                        Log.d(Constant.APP_TAG, SUB_TAG + ": fetchTasks: onNext (type:" + state + ") (" + Thread.currentThread().getName() + ")");
                        mTaskLiveData.setValue(state);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(Constant.APP_TAG, SUB_TAG + ": fetchTasks: onError (" + Thread.currentThread().getName() + ")");
                        mTaskLiveData.setValue(State.error(e));
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
