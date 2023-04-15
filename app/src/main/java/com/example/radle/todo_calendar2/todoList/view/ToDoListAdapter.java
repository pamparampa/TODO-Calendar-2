package com.example.radle.todo_calendar2.todoList.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.radle.todo_calendar2.R;
import com.example.radle.todo_calendar2.todoList.entity.Task;
import com.example.radle.todo_calendar2.todoList.view.util.ToDoListHandler;
import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.view.dto.EncourageElement;
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
    private List<ToDoListElement> toDoListElements;
    private final Mode mode;
    private final ToDoListHandler toDoListHandler;

    private int currentlyEditedPosition = -1;
    private OnItemTextEditListener onItemTextEditListener = (position, mode, Period) -> {
        if (mode != OnItemTextEditListener.DO_NOTHING) {
            changeCurrentlyEditedElementToThis(position);
        }
    };

    private final EditableItemsHolder editableItemsHolder;

    public ToDoListAdapter(Context context, List<ToDoListElement> toDoListElements, Mode mode) {
        this.context = context;
        this.mode = mode;
        this.toDoListHandler = new ToDoListHandler(mode);
        this.toDoListElements = toDoListHandler.fixDtoList(toDoListElements);
        this.editableItemsHolder = new EditableItemsHolder();
    }

    public ToDoListAdapter(Context context, Mode mode, List<Task> tasks) {
        this.context = context;
        this.mode = mode;
        this.toDoListHandler = new ToDoListHandler(mode);
        this.toDoListElements = toDoListHandler.createDtoList(tasks);
        this.editableItemsHolder = new EditableItemsHolder();
    }

    public ToDoListAdapter(Context context, Mode mode) {
        this.context = context;
        this.mode = mode;
        this.toDoListHandler = new ToDoListHandler(mode);
        this.editableItemsHolder = new EditableItemsHolder();
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
            case ToDoListElement.ENCOURAGE_ELEMENT: {
                return getEncourageViewHolder(parent);
            }
        } throw new RuntimeException("Item of To-do list is in unknown type: " + viewType);
    }

    private RecyclerView.ViewHolder getEditableItemViewHolder(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.editable_item_view, parent, false);
        return new EditableItemViewHolder(context, itemView, this::onEditItemText, this::onPeriodChanged);
    }

    private void onPeriodChanged(TaskItemElement editedElement) {
        this.toDoListElements = this.toDoListHandler.fixElement(toDoListElements, editedElement);
        notifyDataSetChanged();
        for (int i = 0; i < this.toDoListElements.size(); i++) {
            if (toDoListElements.get(i).getViewType() == ToDoListElement.VISIBLE_ITEM_VIEW_TYPE
            || toDoListElements.get(i).getViewType() == ToDoListElement.EDITABLE_ITEM_VIEW_TYPE) {
                TaskItemElement taskItemElement = getTaskItemElement(i);
                selectTask(this.toDoListElements.indexOf(taskItemElement));
                return;
            }
        }
    }

    private RecyclerView.ViewHolder getHeaderViewHolder(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.text_list_row, parent, false);
        return new HeaderViewHolder(itemView, this::selectTask);
    }

    private RecyclerView.ViewHolder getVisibleItemViewHolder(@NonNull ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.visible_item_view, parent, false);
        return new VisibleItemViewHolder(itemView, this::selectTask);
    }

    private RecyclerView.ViewHolder getEncourageViewHolder(ViewGroup parent) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.encourage_item_view, parent, false);
        return new EncourageViewHolder(itemView, this::onEditItemText);
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
            case ToDoListElement.ENCOURAGE_ELEMENT: {
                ((EncourageViewHolder) holder).bindWith(getEncourageItemElement(position));
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

    private EncourageElement getEncourageItemElement(int position) {
        return (EncourageElement) toDoListElements.get(position);
    }

    @Override
    public int getItemCount() {
        return this.toDoListElements.size();
    }

    @Override
    public int getItemViewType(int position) {
        return this.toDoListElements.get(position).getViewType();
    }

    private void onEditItemText(int position, int mode, Period period) {
        onItemTextEditListener.onChangeState(position, mode, period);
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

    public int getCurrentlyEditedPosition() {
        return this.currentlyEditedPosition;
    }

    public TaskItemElement getCurrentlyEditedElement() {
        return getTaskItemElement(this.currentlyEditedPosition);
    }

    public List<ToDoListElement> getAllElements() {
        return new ArrayList<>(this.toDoListElements);
    }

    public void setTasks(List<Task> tasks) {
        updateElements(toDoListHandler.createDtoList(tasks));
    }

    public void updateElements(List<ToDoListElement> newElements) {
        this.toDoListElements = this.toDoListHandler.fixDtoList(newElements);
        if (mode == Mode.AT_MOST_ONE_ITEM_IS_IN_EDIT_MODE) {
            stopToEditAllElements();
            changeCurrentlyEditedElementToNone();
        }
        notifyDataSetChanged();
    }

    public void insertElement(int position, ToDoListElement newElement) {
        this.toDoListElements.add(position, newElement);
    }

    public int updateElement(int position, TaskItemElement newElement) {
        this.toDoListElements.set(position, newElement);
        this.toDoListElements = this.toDoListHandler.fixElement(toDoListElements, newElement);
        notifyDataSetChanged();
        return this.toDoListElements.lastIndexOf(newElement);
    }

    public void stopToEditAllElements() {
        this.toDoListElements.forEach(
                element -> {
                    if (element.getViewType() == ToDoListElement.EDITABLE_ITEM_VIEW_TYPE) {
                        ((TaskItemElement) element).setViewType(ToDoListElement.VISIBLE_ITEM_VIEW_TYPE);
                    }
                });
    }

    public int getPositionOf(ToDoListElement element) {
        for (int i = 0 ; i < toDoListElements.size(); i ++) {
            if (toDoListElements.get(i).equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public enum Mode {
        AT_MOST_ONE_ITEM_IS_IN_EDIT_MODE,
        ALL_ITEMS_ARE_IN_EDIT_MODE
    }
}
