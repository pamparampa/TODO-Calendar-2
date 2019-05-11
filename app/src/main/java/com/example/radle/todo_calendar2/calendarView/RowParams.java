package com.example.radle.todo_calendar2.calendarView;

import java.time.LocalDateTime;
import java.util.Objects;

public abstract class RowParams {
    int width = 0;
    int height;
    int numberOfColumns;
    LocalDateTime rowFirsDateTime;

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getNumberOfColumns() {
        return this.numberOfColumns;
    }

    public LocalDateTime getRowFirsDateTime() {
        return this.rowFirsDateTime;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof RowParams)) return false;
        final RowParams rowParams = (RowParams) o;
        return this.width == rowParams.width &&
                this.height == rowParams.height &&
                this.numberOfColumns == rowParams.numberOfColumns &&
                Objects.equals(this.rowFirsDateTime, rowParams.rowFirsDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.width, this.height, this.numberOfColumns, this.rowFirsDateTime);
    }

    @Override
    public String toString() {
        return "RowParams{" +
                "width=" + this.width +
                ", height=" + this.height +
                ", numberOfColumns=" + this.numberOfColumns +
                ", rowFirsDateTime=" + this.rowFirsDateTime +
                '}';
    }
}
