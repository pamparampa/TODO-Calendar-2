package com.example.radle.todo_calendar2.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.constant.RequestCodes;
import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.view.dto.EditableItemElement;
import com.example.radle.todo_calendar2.todoList.view.dto.HeaderElement;
import com.example.radle.todo_calendar2.todoList.view.ToDoListAdapter;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTaskMessageActivity extends FragmentActivity {
    @BindView(R.id.todoRecyclerView)
    RecyclerView todoRecyclerView;
    private ToDoListAdapter toDoListAdapter;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task_message_activity);
        ButterKnife.bind(this);
        initToDoList();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RequestCodes.EDIT_TASK_MESSAGE, intent);
        finish();
    }

    private void initToDoList() {
        this.todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.toDoListAdapter = new ToDoListAdapter(this,
                Arrays.asList(
                        new HeaderElement("DZISIAJ"),
                        new EditableItemElement("zrób to", Period.TODAY),
                        new EditableItemElement("oraz tamto", Period.TODAY),
                        new HeaderElement("JUTRO")));
        this.todoRecyclerView.setAdapter(toDoListAdapter);
        todoRecyclerView.addOnLayoutChangeListener(
                (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) ->
                        EditTaskMessageActivity.this.toDoListAdapter.startToEditItemMessage(1));
    }
}
