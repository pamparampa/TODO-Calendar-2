package com.example.radle.todo_calendar2.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.constant.RequestCodes;
import com.example.radle.todo_calendar2.todoList.view.ToDoListAdapter;
import com.example.radle.todo_calendar2.todoList.view.dto.TaskItemElement;
import com.example.radle.todo_calendar2.todoList.view.dto.ToDoListElement;
import com.example.radle.todo_calendar2.todoList.view.util.ToDoListHandler;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditTaskActivity extends FragmentActivity {
    @BindView(R.id.todoRecyclerView)
    RecyclerView todoRecyclerView;
    private ToDoListAdapter toDoListAdapter;
    private ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
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
        if (someTaskHasEmptyMessage()) {
            showSomeTaskIsEmptyMessage();
            return;
        }
        TaskItemElement currentlyEditedElement = this.toDoListAdapter.getCurrentlyEditedElement();
        this.toDoListAdapter.updateElements(this.toDoListHandler.fixElement(
                this.toDoListAdapter.getAllElements(), currentlyEditedElement));
        Intent intent = new Intent();
        intent.putExtra(Literals.ALL_TODO_ELEMENTS, new ArrayList<Parcelable> (this.toDoListAdapter.getAllElements()));
        intent.putExtra(Literals.EDITED_ITEM_INDEX, this.toDoListAdapter.getPositionOf(currentlyEditedElement));
        setResult(RequestCodes.EDIT_TASK_MESSAGE, intent);
        finish();
    }

    private void showSomeTaskIsEmptyMessage() {
        Toast toast = Toast.makeText(this,
                "you have some empty task message. You have to complete it", Toast.LENGTH_SHORT);
        toast.show();
    }

    private boolean someTaskHasEmptyMessage() {
        return toDoListAdapter.getAllElements().stream()
        .anyMatch( element -> isElementEditableAndHasEmptyMessage(element));
    }

    private boolean isElementEditableAndHasEmptyMessage(ToDoListElement element) {
        if (element.getViewType() == ToDoListElement.EDITABLE_ITEM_VIEW_TYPE) {
            return ((TaskItemElement) element).getTaskTitle().isEmpty();
        }
        return false;
    }

    private void initToDoList() {
        this.todoRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        this.toDoListAdapter = new ToDoListAdapter(this,
                getIntent().getParcelableArrayListExtra(Literals.ALL_TODO_ELEMENTS),
                ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
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
