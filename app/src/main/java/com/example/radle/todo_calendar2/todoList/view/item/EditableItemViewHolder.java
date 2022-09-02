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
import com.example.radle.todo_calendar2.todoList.view.dto.EditableItemElement;

public class EditableItemViewHolder extends RecyclerView.ViewHolder {
    private final EditText taskNameEditText;
    private final Spinner spinner;
    private EditableItemElement editableItemElement;

    public EditableItemViewHolder(Context context, View itemView, OnItemTextEditListener onItemTextEditListener) {
        super(itemView);
        this.taskNameEditText = itemView.findViewById(R.id.taskNameEditText);
        this.taskNameEditText.addTextChangedListener(createTextChangedListener());
        this.taskNameEditText.setOnFocusChangeListener((view, isFocusOn) -> {
            onItemTextEditListener.onChangeState(isFocusOn);
        });
        this.spinner = itemView.findViewById(R.id.spinner);
        this.spinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Period.getNames()));
    }

    public void bindWith(EditableItemElement editableItemElement) {
        this.editableItemElement = editableItemElement;
        this.taskNameEditText.setText(editableItemElement.getTaskTitle());
    }

    public void forceToEditName() {
        this.taskNameEditText.requestFocus(View.FOCUS_LEFT);
    }

    private TextWatcher createTextChangedListener() {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                EditableItemViewHolder.this.editableItemElement.setTaskTitle(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
    }
}