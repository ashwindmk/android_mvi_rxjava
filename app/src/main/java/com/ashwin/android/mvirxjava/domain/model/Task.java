package com.ashwin.android.mvirxjava.domain.model;

public class Task {
    private long id;
    private String title;
    private int priority;
    private boolean isComplete;

    public Task(long id, String title, int priority, boolean isComplete) {
        this.id = id;
        this.title = title;
        this.priority = priority;
        this.isComplete = isComplete;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", priority=" + priority +
                ", isComplete=" + isComplete +
                '}';
    }
}
