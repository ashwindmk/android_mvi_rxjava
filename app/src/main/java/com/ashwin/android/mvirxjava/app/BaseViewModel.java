package com.ashwin.android.mvirxjava.app;

import androidx.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {
    protected CompositeDisposable mCompositeDisposable;

    public BaseViewModel() {
        mCompositeDisposable = new CompositeDisposable();
    }

    protected void autoDispose(Disposable d) {
        mCompositeDisposable.add(d);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mCompositeDisposable.dispose();
    }
}
