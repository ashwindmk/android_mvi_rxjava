package com.ashwin.android.mvirxjava.app.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// You can use such a contract if yous use-case does not meet with the State or Event class.
interface HomeContract {
    // Event
    int GET_ALL = 1;

    // State
    int INITIAL = 0;
    int LOADING = 1;
    int SUCCESS = 2;
    int ERROR = 3;

    class HomeEvent {
        private final int event;

        private HomeEvent(int e) {
            this.event = e;
        }

        public static HomeEvent getAll() {
            return new HomeEvent(GET_ALL);
        }

        public int getType() {
            return event;
        }
    }

    class HomeState<T> {
        private final int state;
        private final T data;
        private final Throwable error;

        private HomeState(@NonNull int state, @Nullable T d, @Nullable Throwable e) {
            this.state = state;
            this.data = d;
            this.error = e;
        }

        public static <T> HomeState<T> loading(T d) {
            return new HomeState<>(LOADING, null, null);
        }

        public static <T> HomeState<T> success(T d) {
            return new HomeState<>(SUCCESS, d, null);
        }

        public static <T> HomeState<T> error(Throwable e) {
            return new HomeState<>(ERROR, null, e);
        }

        public int getState() {
            return state;
        }

        public T getData() {
            return data;
        }

        public Throwable getError() {
            return error;
        }
    }
}
