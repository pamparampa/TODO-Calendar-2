package com.example.radle.todo_calendar2.todoList.view.dto;

import android.os.Parcel;

import java.util.Objects;

public class HeaderElement implements ToDoListElement {
    private final String title;

    public HeaderElement(String title) {
        this.title = title;
    }

    public static final Creator<HeaderElement>  CREATOR = new Creator<HeaderElement>() {
        @Override
        public HeaderElement createFromParcel(final Parcel in) {
            return new HeaderElement(in);
        }

        @Override
        public HeaderElement[] newArray(final int size) {
            return new HeaderElement[size];
        }
    };

    public HeaderElement(Parcel in) {
        in.readInt();
        this.title = in.readString();
    }

    @Override
    public int getViewType() {
        return ToDoListElement.HEADER_VIEW_TYPE;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getViewType());
        dest.writeString(this.title);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeaderElement that = (HeaderElement) o;
        return title.equals(that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "HeaderElement{" +
                "title='" + title + '\'' +
                '}';
    }
}
