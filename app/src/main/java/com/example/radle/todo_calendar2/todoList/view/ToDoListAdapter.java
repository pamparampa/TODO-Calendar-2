package com.example.radle.todo_calendar2.todoList.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.view.dto.EditableItemElement;
import com.example.radle.todo_calendar2.todoList.view.dto.HeaderElement;
import com.example.radle.todo_calendar2.todoList.view.dto.ToDoListElement;
import com.example.radle.todo_calendar2.todoList.view.dto.VisibleItemElement;
import com.example.radle.todo_calendar2.todoList.view.item.EditableItemViewHolder;
import com.example.radle.todo_calendar2.todoList.view.item.HeaderViewHolder;
import com.example.radle.todo_calendar2.todoList.view.item.VisibleItemViewHolder;

import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    //@Bind
    private final List<ToDoListElement> toDoListElements;

    private int currentlyEditedPosition = -1;
    private OnItemTextEditListener onItemTextEditListener = isInEditMode -> {};
    private final EditableItemsHolder editableItemsHolder = new EditableItemsHolder();

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
                return new HeaderViewHolder(itemView, this::selectTask);
            }
            case ToDoListElement.VISIBLE_ITEM_VIEW_TYPE: {
                itemView = LayoutInflater.from(context).inflate(R.layout.visible_item_view, parent, false);
                return new VisibleItemViewHolder(itemView, this::selectTask);
            }
            case ToDoListElement.EDITABLE_ITEM_VIEW_TYPE: {
                itemView = LayoutInflater.from(context).inflate(R.layout.editable_item_view, parent, false);
                return new EditableItemViewHolder(context, itemView, this.onItemTextEditListener);
            }
        } throw new RuntimeException("Item of To-do list is in unknown type: " + viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch(getItemViewType(position)) {
            case ToDoListElement.HEADER_VIEW_TYPE : {
                ((HeaderViewHolder)holder).bindWith(getHeaderElement(position));
                break;
            }
            case ToDoListElement.VISIBLE_ITEM_VIEW_TYPE : {
                ((VisibleItemViewHolder) holder).bindWith(getVisibleItemElement(position));
                break;
            }
            case ToDoListElement.EDITABLE_ITEM_VIEW_TYPE: {
                EditableItemViewHolder editableItem = (EditableItemViewHolder) holder;
                editableItem.bindWith(getEditableItemElement(position));
                this.editableItemsHolder.set(position, editableItem);
                break;
            }
        }
    }

    private VisibleItemElement getVisibleItemElement(int position) {
        return (VisibleItemElement) toDoListElements.get(position);
    }

    private HeaderElement getHeaderElement(int position) {
        return (HeaderElement) toDoListElements.get(position);
    }

    private EditableItemElement getEditableItemElement(int position) {
        return (EditableItemElement) toDoListElements.get(position);
    }

    @Override
    public int getItemCount() {
        return this.toDoListElements.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.toDoListElements.get(position).getViewType();
    }

    private void selectTask(int position) {
        if(currentlyEditedPosition != -1) {
            stopToEditCurrentElement();
        }
        if (this.toDoListElements.get(position).getViewType() == ToDoListElement.VISIBLE_ITEM_VIEW_TYPE) {
            startToEditSelectedElement(position);
            this.currentlyEditedPosition = position;
        } else {
            this.currentlyEditedPosition = -1;
            this.onItemTextEditListener.onChangeState(false);
        }
        notifyDataSetChanged();
    }

    public void finishEditing() {
        stopToEditCurrentElement();
        notifyDataSetChanged();
    }

    private void startToEditSelectedElement(int position) {
        this.toDoListElements.set(position,
                new EditableItemElement(getVisibleItemElement(position).getTaskTitle(), Period.SOMETIME));
    }

    private void stopToEditCurrentElement() {
        this.toDoListElements.set(currentlyEditedPosition,
                new VisibleItemElement(getEditableItemElement(currentlyEditedPosition).getTaskTitle(), false));
    }

    public void setOnItemTextEditListener(OnItemTextEditListener listener) {
        this.onItemTextEditListener = listener;
    }

    public void startToEditItemMessage(int itemPosition) {
        this.editableItemsHolder.editName(itemPosition);
    }
}
