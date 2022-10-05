package com.example.radle.todo_calendar2.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.constant.RequestCodes;
import com.example.radle.todo_calendar2.todoList.view.ToDoListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTaskActivity extends FragmentActivity {
    @BindView(R.id.todoRecyclerView)
    RecyclerView todoRecyclerView;
    private ToDoListAdapter toDoListAdapter;
    private boolean actionPerformedFirstTime = true;

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
        intent.putExtra(Literals.EDITED_ITEM_INDEX, this.toDoListAdapter.getCurrentlyEditedItem());
        intent.putExtra(Literals.ALL_TODO_ELEMENTS, this.toDoListAdapter.getAllElements());
        setResult(RequestCodes.EDIT_TASK_MESSAGE, intent);
        finish();
    }

    private void initToDoList() {
        this.todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.toDoListAdapter = new ToDoListAdapter(this,
                getIntent().getParcelableArrayListExtra(Literals.ALL_TODO_ELEMENTS),
                ToDoListAdapter.Mode.EVERY_ITEMS_ARE_IN_EDIT_MODE);
        this.todoRecyclerView.setAdapter(toDoListAdapter);
        todoRecyclerView.addOnLayoutChangeListener(
                (v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom) -> startEditingItemOnTheBeginning());
    }

    private void startEditingItemOnTheBeginning() {
        if (actionPerformedFirstTime) {
            EditTaskActivity.this.toDoListAdapter.startToEditItemTitle(getFirstEditedItemIndex());
            actionPerformedFirstTime = false;
        }
    }

    private int getFirstEditedItemIndex() {
        return getIntent().getIntExtra(Literals.EDITED_ITEM_INDEX, 1);
    }
}
