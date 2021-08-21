package com.ashwin.android.mvirxjava.app;

public class Event<T> {
    // Event
    public static final int GET_ALL = 1;
    public static final int GET_ONE = 2;

    private final int type;
    private final T data;

    private Event(int type, T data) {
        this.type = type;
        this.data = data;
    }

    public static <T> Event<T> getEvent(int type, T data) {
        return new Event<>(type, data);
    }

    public int getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}
