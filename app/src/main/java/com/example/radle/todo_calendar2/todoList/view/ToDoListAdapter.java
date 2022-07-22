package com.example.radle.todo_calendar2.todoList.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter {
    private Context context;
    //@Bind
    private List<ToDoListElement> toDoListElements;

    public ToDoListAdapter(Context context, List<ToDoListElement> toDoListElements) {
        this.context = context;
        this.toDoListElements = toDoListElements;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        switch (viewType) {
            case ToDoListElement.HEADER_VIEW_TYPE : {
                itemView = LayoutInflater.from(context).inflate(R.layout.text_list_row, parent, false);
                return new HeaderViewHolder(itemView);
            }
            case ToDoListElement.VISIBLE_ITEM_VIEW_TYPE: {
                itemView = LayoutInflater.from(context).inflate(R.layout.todo_row_view, parent, false);
                return new VisibleItemViewHolder(itemView);
            }
        } return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == ToDoListElement.HEADER_VIEW_TYPE) {
            ((HeaderViewHolder)holder).bindWith(getHeaderElement(position));
        } else if (getItemViewType(position) == ToDoListElement.VISIBLE_ITEM_VIEW_TYPE) {
            ((VisibleItemViewHolder)holder).bindWith(getVisibleItemElement(position));
        }
    }

    private VisibleItemElement getVisibleItemElement(int position) {
        return (VisibleItemElement) toDoListElements.get(position);
    }

    private HeaderElement getHeaderElement(int position) {
        return (HeaderElement) toDoListElements.get(position);
    }

    @Override
    public int getItemCount() {
        return this.toDoListElements.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.toDoListElements.get(position).getViewType();
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.todoLabel);
        }

        public void bindWith(HeaderElement headerElement) {
            this.textView.setText(headerElement.getTitle());
        }
    }

    private static class VisibleItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView taskNameView;
        private final CheckBox checkBox;

        public VisibleItemViewHolder(View itemView) {
            super(itemView);
            this.taskNameView = itemView.findViewById(R.id.taskNameView);
            this.checkBox = itemView.findViewById(R.id.checkBox);
        }


        public void bindWith(VisibleItemElement visibleItemElement) {
            this.taskNameView.setText(visibleItemElement.getTaskTitle());
            this.checkBox.setChecked(visibleItemElement.isFinished());
        }
    }
}
