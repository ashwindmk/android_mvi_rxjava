package com.ashwin.android.mvirxjava.app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class State<T> {
    // States
    public static final int INITIAL = 0;
    public static final int LOADING = 1;
    public static final int SUCCESS = 2;
    public static final int ERROR = 3;

    private final int type;
    private final T data;
    private final Throwable error;
    private final String message;

    private State(@NonNull int type, @Nullable T d, @Nullable Throwable e, @Nullable String message) {
        this.type = type;
        this.data = d;
        this.error = e;
        this.message = message;
    }

    public static <T> State<T> initial() {
        return new State<>(INITIAL, null, null, null);
    }

    public static <T> State<T> loading() {
        return new State<>(LOADING, null, null, "Loading...");
    }

    public static <T> State<T> success(T d) {
        return new State<>(SUCCESS, d, null, null);
    }

    public static <T> State<T> error(Throwable e) {
        return new State<>(ERROR, null, e, e.getMessage());
    }

    public int getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    public Throwable getError() {
        return error;
    }

    @Override
    public String toString() {
        return "State{" +
                "type=" + type +
                ", data=" + data +
                ", error=" + error +
                ", message='" + message + '\'' +
                '}';
    }
}
