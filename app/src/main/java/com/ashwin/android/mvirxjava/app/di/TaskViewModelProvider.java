package com.ashwin.android.mvirxjava.app.di;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ashwin.android.mvirxjava.app.detail.DetailViewModel;
import com.ashwin.android.mvirxjava.app.home.HomeViewModel;
import com.ashwin.android.mvirxjava.data.repositoryimpl.TaskRepositoryImpl;
import com.ashwin.android.mvirxjava.data.source.local.LocalTaskSource;

public class TaskViewModelProvider implements ViewModelProvider.Factory {
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(HomeViewModel.class)) {
            return (T) new HomeViewModel(new TaskRepositoryImpl(new LocalTaskSource()));
        }
        if (modelClass.isAssignableFrom(DetailViewModel.class)) {
            return (T) new DetailViewModel(new TaskRepositoryImpl(new LocalTaskSource()));
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
