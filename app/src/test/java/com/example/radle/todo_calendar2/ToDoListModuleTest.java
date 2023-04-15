package com.example.radle.todo_calendar2;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.activity.MainActivity;
import com.example.radle.todo_calendar2.todoList.view.ToDoListAdapter;

import junit.framework.TestCase;

import org.junit.Test;
import org.powermock.reflect.Whitebox;

public class ToDoListModuleTest {
    MainActivity mainActivity;
    ToDoListAdapter toDoListAdapter;
    RecyclerView toDoRecyclerView;

    @Test
    public void test() {
        this.mainActivity = new MainActivityStub();
        toDoListAdapter.getAllElements();
    }

    private class MainActivityStub extends MainActivity {
        MainActivityStub() {
            Whitebox.setInternalState(this,
                    new ToDoListAdapterStub(this, ToDoListAdapter.Mode.AT_MOST_ONE_ITEM_IS_IN_EDIT_MODE),
                    new RecyclerView(this));
            initToDoList();
        }
    }

    private class ToDoListAdapterStub extends ToDoListAdapter {
        public ToDoListAdapterStub(Context context, Mode mode) {
            super(context, mode);
        }
    }

    private class RecyclerViewStub extends RecyclerView {

        public RecyclerViewStub(@NonNull Context context) {
            super(context);
        }


    }
}