package com.example.radle.todo_calendar2.todoList.view.item;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.todoList.view.OnItemClickListener;
import com.example.radle.todo_calendar2.todoList.view.dto.VisibleItemElement;

public class VisibleItemViewHolder extends RecyclerView.ViewHolder {

    private final TextView taskNameView;
    private final CheckBox checkBox;

    public VisibleItemViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        this.taskNameView = itemView.findViewById(R.id.taskNameView);
        this.taskNameView.setOnClickListener(view -> onItemClickListener.onClick(getAdapterPosition()));
        this.checkBox = itemView.findViewById(R.id.checkBox);
    }

    public void bindWith(VisibleItemElement visibleItemElement) {
        this.taskNameView.setText(visibleItemElement.getTaskTitle());
        this.checkBox.setChecked(visibleItemElement.isDone());
    }
}