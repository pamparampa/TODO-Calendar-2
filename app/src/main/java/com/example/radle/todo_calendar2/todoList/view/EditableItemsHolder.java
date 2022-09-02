package com.example.radle.todo_calendar2.todoList.view;
import com.example.radle.todo_calendar2.todoList.view.item.EditableItemViewHolder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditableItemsHolder {
    Map<Integer, EditableItemViewHolder> editableItems = new HashMap<>();
    int editedItemPosition = -1;

    public void set(int position, EditableItemViewHolder editableItem) {
        this.editableItems.put(position, editableItem);
    }

    public void editName(int itemPosition) {
        if(editableItems.containsKey(itemPosition)) {
            Objects.requireNonNull(this.editableItems.get(itemPosition)).forceToEditName();
            this.editedItemPosition = itemPosition;
        }
    }
}
