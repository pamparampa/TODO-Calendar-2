package com.example.radle.todo_calendar2.todoList.view;

import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.view.dto.EncourageElement;

public class EncourageViewHolder extends RecyclerView.ViewHolder {
    private final EditText encourageEditText;
    private Period period;
    private String message;
    private boolean isFirstEncourage;

    public EncourageViewHolder(View itemView, OnItemTextEditListener onItemTextEditListener) {
        super(itemView);
        this.encourageEditText = itemView.findViewById(R.id.encourageEditText);
        this.encourageEditText.setOnFocusChangeListener((view, isFocusOn) ->
                    onFocusChanged(onItemTextEditListener, isFocusOn)
        );

    }

    private void onFocusChanged(OnItemTextEditListener onItemTextEditListener, boolean isFocusOn) {
        if (isFocusOn) {
            onItemTextEditListener.onChangeState(getAdapterPosition(), isFirstEncourage?
                    OnItemTextEditListener.CREATE_FIRST_TASK: OnItemTextEditListener.CREATE_NEW_TASK,
                    period);
        }
    }

    public void bindWith(EncourageElement encourageItemElement) {
        this.period = encourageItemElement.getPeriod();
        this.message = encourageItemElement.getMessage();
        this.isFirstEncourage = encourageItemElement.isFirstEncourage();
        this.encourageEditText.setText(message);
    }
}
