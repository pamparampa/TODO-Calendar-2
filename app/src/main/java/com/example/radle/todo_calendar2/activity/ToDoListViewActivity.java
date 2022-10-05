package com.example.radle.todo_calendar2.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.view.dto.HeaderElement;
import com.example.radle.todo_calendar2.todoList.view.ToDoListAdapter;
import com.example.radle.todo_calendar2.todoList.view.dto.TaskItemElement;
import com.example.radle.todo_calendar2.todoList.view.dto.ToDoListElement;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToDoListViewActivity extends Activity {
    @BindView(R.id.todoRecyclerView2)
    RecyclerView todoRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list_view_activity);
        ButterKnife.bind(this);
        this.todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.todoRecyclerView.setAdapter(new ToDoListAdapter(this,
                Arrays.asList(
                        new HeaderElement("DZISIAJ"),
                        new TaskItemElement("zrób to", Period.TODAY, false,
                                ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        new TaskItemElement("oraz tamto", Period.TODAY, false,
                                ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        new HeaderElement("JUTRO")),
                ToDoListAdapter.Mode.AT_MOST_ONE_ITEM_IS_IN_EDIT_MODE));
    }
}
