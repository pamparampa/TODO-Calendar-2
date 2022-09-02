package com.example.radle.todo_calendar2.todoList.view.item;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.todoList.view.OnItemClickListener;
import com.example.radle.todo_calendar2.todoList.view.dto.HeaderElement;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    private final TextView textView;

    public HeaderViewHolder(View itemView, OnItemClickListener onItemClickListener) {
        super(itemView);
        this.textView = itemView.findViewById(R.id.todoLabel);
        this.textView.setOnClickListener(view -> onItemClickListener.onClick(getAdapterPosition()));
    }

    public void bindWith(HeaderElement headerElement) {
        this.textView.setText(headerElement.getTitle());
    }
}