package com.example.radle.todo_calendar2.todoList.view;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.todoList.view.dto.TaskItemElement;
import com.example.radle.todo_calendar2.todoList.view.dto.HeaderElement;
import com.example.radle.todo_calendar2.todoList.view.dto.ToDoListElement;
import com.example.radle.todo_calendar2.todoList.view.item.EditableItemViewHolder;
import com.example.radle.todo_calendar2.todoList.view.item.HeaderViewHolder;
import com.example.radle.todo_calendar2.todoList.view.item.VisibleItemViewHolder;

import java.util.ArrayList;
import java.util.List;

public class ToDoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    //@Bind
    private List<ToDoListElement> toDoListElements;
    private final Mode mode;

    private int currentlyEditedPosition = -1;
    private OnItemTextEditListener onItemTextEditListener = (position, isInEditMode) -> {
        if (isInEditMode) {
            changeCurrentlyEditedElementToThis(position);
        }
    };

    private final EditableItemsHolder editableItemsHolder = new EditableItemsHolder();

    public ToDoListAdapter(Context context, List<ToDoListElement> toDoListElements, Mode mode) {
        this.context = context;
        this.toDoListElements = toDoListElements;
        this.mode = mode;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case ToDoListElement.HEADER_VIEW_TYPE : {
                return getHeaderViewHolder(parent);
            }
            case ToDoListElement.VISIBLE_ITEM_VIEW_TYPE: {
                return getVisibleItemViewHolder(parent);
            }
            case ToDoListElement.EDITABLE_ITEM_VIEW_TYPE: {
                return getEditableItemViewHolder(parent);
            }
        } throw new RuntimeException("Item of To-do list is in unknown type: " + viewType);
    }

    private RecyclerView.ViewHolder getEditableItemViewHolder(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.editable_item_view, parent, false);
        return new EditableItemViewHolder(context, itemView, this::onEditItemText);
    }

    private RecyclerView.ViewHolder getHeaderViewHolder(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.text_list_row, parent, false);
        return new HeaderViewHolder(itemView, this::selectTask);
    }

    private RecyclerView.ViewHolder getVisibleItemViewHolder(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.visible_item_view, parent, false);
        return new VisibleItemViewHolder(itemView, this::selectTask);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch(getItemViewType(position)) {
            case ToDoListElement.HEADER_VIEW_TYPE : {
                ((HeaderViewHolder)holder).bindWith(getHeaderElement(position));
                break;
            }
            case ToDoListElement.VISIBLE_ITEM_VIEW_TYPE : {
                ((VisibleItemViewHolder) holder).bindWith(getTaskItemElement(position));
                break;
            }
            case ToDoListElement.EDITABLE_ITEM_VIEW_TYPE: {
                onBindEditableViewHolder((EditableItemViewHolder) holder, position);
                break;
            }
        }
    }

    private void onBindEditableViewHolder(@NonNull EditableItemViewHolder holder, int position) {
        holder.bindWith(getTaskItemElement(position));
        this.editableItemsHolder.set(position, holder);
    }

    private HeaderElement getHeaderElement(int position) {
        return (HeaderElement) toDoListElements.get(position);
    }

    private TaskItemElement getTaskItemElement(int position) {
        return (TaskItemElement) toDoListElements.get(position);
    }

    @Override
    public int getItemCount() {
        return this.toDoListElements.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.toDoListElements.get(position).getViewType();
    }

    private void onEditItemText(int position, boolean isInEditedState) {
        onItemTextEditListener.onChangeState(position, isInEditedState);
    }

    private void selectTask(int position) {
        if(currentElementShouldNotBeEditedAnyMore()) {
            stopToEditCurrentElement();
        }
        int viewType = this.toDoListElements.get(position).getViewType();

        if (viewType == ToDoListElement.VISIBLE_ITEM_VIEW_TYPE) {
            startToEditSelectedElement(position);
        } else if (viewType == ToDoListElement.EDITABLE_ITEM_VIEW_TYPE) {
            changeCurrentlyEditedElementToThis(position);
        } else if (viewType == ToDoListElement.HEADER_VIEW_TYPE &&
                mode == Mode.AT_MOST_ONE_ITEM_IS_IN_EDIT_MODE){
            changeCurrentlyEditedElementToNone();
        }
        notifyDataSetChanged();
    }

    private void changeCurrentlyEditedElementToNone() {
        this.currentlyEditedPosition = -1;
    }

    private void changeCurrentlyEditedElementToThis(int position) {
        this.currentlyEditedPosition = position;
    }

    public void startToEditElement(int position) {
        if (currentlyEditedPosition != position) {
            if (currentElementShouldNotBeEditedAnyMore()) {
                stopToEditCurrentElement();
            }
            startToEditSelectedElement(position);
        }
        notifyDataSetChanged();
    }

    private boolean currentElementShouldNotBeEditedAnyMore() {
        return this.mode == Mode.AT_MOST_ONE_ITEM_IS_IN_EDIT_MODE && currentlyEditedPosition != -1;
    }

    private void startToEditSelectedElement(int position) {
        changeCurrentlyEditedElementToThis(position);
        ToDoListElement elementToEdit = toDoListElements.get(position);
        if (elementToEdit.getViewType() == ToDoListElement.VISIBLE_ITEM_VIEW_TYPE) {
            getTaskItemElement(position).setViewType(ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        }
    }

    private void stopToEditCurrentElement() {
        getTaskItemElement(currentlyEditedPosition).setViewType(ToDoListElement.VISIBLE_ITEM_VIEW_TYPE);
    }

    public void setOnItemTextEditListener(OnItemTextEditListener listener) {
        this.onItemTextEditListener = listener;
    }

    public void startToEditItemTitle(int itemPosition) {
        changeCurrentlyEditedElementToThis(itemPosition);
        this.editableItemsHolder.startToEditTitle(itemPosition);
    }

    public int getCurrentlyEditedItem() {
        return this.currentlyEditedPosition;
    }

    public ArrayList<? extends Parcelable> getAllElements() {
        return new ArrayList<>(this.toDoListElements);
    }

    public void updateElements(ArrayList<? extends ToDoListElement> newElements) {
        this.toDoListElements = new ArrayList<>(newElements);
        notifyDataSetChanged();
    }

    public enum Mode {
        AT_MOST_ONE_ITEM_IS_IN_EDIT_MODE,
        EVERY_ITEMS_ARE_IN_EDIT_MODE
    }
}
