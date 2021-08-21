package com.ashwin.android.mvirxjava.domain.repository;

import com.ashwin.android.mvirxjava.domain.model.Task;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public interface TaskRepository {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    Observable<List<Task>> findAll();

    Observable<Task> findById(long id);

    default void close() {
        compositeDisposable.dispose();
    }
}
