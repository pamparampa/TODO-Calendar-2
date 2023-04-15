package com.example.radle.todo_calendar2.todoList.view.item;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.view.OnItemTextEditListener;
import com.example.radle.todo_calendar2.todoList.view.dto.TaskItemElement;

import java.util.function.Consumer;

public class EditableItemViewHolder extends RecyclerView.ViewHolder {
    private final EditText taskTitleEditText;
    private TaskItemElement taskItemElement;
    private final Spinner periodSpinner;

    public EditableItemViewHolder(Context context, View itemView,
                                  OnItemTextEditListener onItemTextEditListener,
                                  Consumer<TaskItemElement> onPeriodChangedListener) {
        super(itemView);
        this.taskTitleEditText = itemView.findViewById(R.id.taskTitleEditText);
        this.taskTitleEditText.addTextChangedListener(createTextChangedListener());
        this.taskTitleEditText.setOnFocusChangeListener((view, isFocusOn) ->
                onFocusChanged(context, onItemTextEditListener, isFocusOn));
        this.periodSpinner = itemView.findViewById(R.id.spinner);
        this.periodSpinner.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, Period.getNames()));
        this.periodSpinner.setOnItemSelectedListener(createOnItemSelectedListener(onPeriodChangedListener));
    }

    private void onFocusChanged(Context context, OnItemTextEditListener onItemTextEditListener, boolean isFocusOn) {
        if (!isFocusOn && isMessageEmpty()) {
            showTaskCannotBeEmptyMessage(context);
        } else {
            onItemTextEditListener.onChangeState(getAdapterPosition(), chooseMode(isFocusOn),
                    taskItemElement.getPeriod());
        }
    }

    private void showTaskCannotBeEmptyMessage(Context context) {
        Toast toast = Toast.makeText(context, "Task message cannot be empty!", Toast.LENGTH_SHORT);
        toast.show();
    }

    private boolean isMessageEmpty() {
        return taskTitleEditText.getText().chars().count() == 0;
    }

    private int chooseMode(boolean isFocusOn) {
        return isFocusOn? OnItemTextEditListener.EDIT_EXISTING_TASK:
                OnItemTextEditListener.DO_NOTHING;
    }

    public void bindWith(TaskItemElement taskItemElement) {
        this.taskItemElement = taskItemElement;
        this.taskTitleEditText.setText(taskItemElement.getTaskTitle());
        this.periodSpinner.setSelection(findIdOfPeriod(taskItemElement.getPeriod()));
    }

    private int findIdOfPeriod(Period period) {
        SpinnerAdapter spinnerAdapter = periodSpinner.getAdapter();
        for (int i = 0; i < spinnerAdapter.getCount(); i++) {
            if(period.name().equals(spinnerAdapter.getItem(i))) {
                return i;
            }
        }
        return -1;
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

    private AdapterView.OnItemSelectedListener createOnItemSelectedListener(Consumer<TaskItemElement> onPeriodChangedListener) {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Period oldPeriod = EditableItemViewHolder.this.taskItemElement.getPeriod();
                Period newPeriod = Period.valueOf(
                        (String) EditableItemViewHolder.this.periodSpinner.getAdapter().getItem((int) id));
                if(oldPeriod != newPeriod) {
                    EditableItemViewHolder.this.taskItemElement.setPeriod(newPeriod);
                    onPeriodChangedListener.accept(EditableItemViewHolder.this.taskItemElement);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }
}