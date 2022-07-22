package com.example.radle.todo_calendar2.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.todoList.view.HeaderElement;
import com.example.radle.todo_calendar2.todoList.view.ToDoListAdapter;
import com.example.radle.todo_calendar2.todoList.view.VisibleItemElement;

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
                        new VisibleItemElement("zrób to", false),
                        new VisibleItemElement("oraz tamto", false),
                        new HeaderElement("JUTRO"))));
    }
}
