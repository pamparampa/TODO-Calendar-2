package com.example.radle.todo_calendar2.todoList.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task implements Parcelable {

    public Task(String title, boolean isDone, Period period) {
        this.title = title;
        this.isDone = isDone;
        this.period = period;
    }

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "is_done")
    public boolean isDone;

    @ColumnInfo(name = "period")
    public Period period;

    protected Task(Parcel in) {
        id = in.readInt();
        title = in.readString();
        isDone = in.readByte() != 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", isDone=" + isDone +
                ", period=" + period +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.title);
        parcel.writeBoolean(this.isDone);
        parcel.writeString(this.period.name());
    }
}
