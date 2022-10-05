package com.example.radle.todo_calendar2.todoList.view.item;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.view.OnItemTextEditListener;
import com.example.radle.todo_calendar2.todoList.view.dto.TaskItemElement;

public class EditableItemViewHolder extends RecyclerView.ViewHolder {
    private final EditText taskTitleEditText;
    private TaskItemElement taskItemElement;

    public EditableItemViewHolder(Context context, View itemView,
                                  OnItemTextEditListener onItemTextEditListener) {
        super(itemView);
        this.taskTitleEditText = itemView.findViewById(R.id.taskTitleEditText);
        this.taskTitleEditText.addTextChangedListener(createTextChangedListener());
        this.taskTitleEditText.setOnFocusChangeListener((view, isFocusOn) ->
                onItemTextEditListener.onChangeState(getAdapterPosition(), isFocusOn));

        Spinner spinner = itemView.findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Period.getNames()));
    }

    public void bindWith(TaskItemElement taskItemElement) {
        this.taskItemElement = taskItemElement;
        this.taskTitleEditText.setText(taskItemElement.getTaskTitle());
    }

    public void forceToEditTitle() {
        this.taskTitleEditText.requestFocus(View.FOCUS_LEFT);
    }

    private TextWatcher createTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                EditableItemViewHolder.this.taskItemElement.setTaskTitle(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }
}