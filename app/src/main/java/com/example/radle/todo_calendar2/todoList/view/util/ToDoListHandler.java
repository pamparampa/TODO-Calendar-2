package com.example.radle.todo_calendar2.todoList.view.util;

import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.entity.Task;
import com.example.radle.todo_calendar2.todoList.view.ToDoListAdapter;
import com.example.radle.todo_calendar2.todoList.view.dto.EncourageElement;
import com.example.radle.todo_calendar2.todoList.view.dto.HeaderElement;
import com.example.radle.todo_calendar2.todoList.view.dto.TaskItemElement;
import com.example.radle.todo_calendar2.todoList.view.dto.ToDoListElement;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class ToDoListHandler {

    private final ToDoListAdapter.Mode mode;

    public ToDoListHandler(ToDoListAdapter.Mode mode) {
        this.mode = mode;
    }

    public List<ToDoListElement> createDtoList(List<Task> tasksFromDB) {
        if (tasksFromDB.isEmpty()) {
            return new ArrayList<ToDoListElement>() {{
                add(EncourageElement.ENCOURAGE_TO_ADD_FIRST_TASK);
            }};
        } else {
            Multimap<Period, Task> tasksByPeriods = groupTasksByPeriods(tasksFromDB);
            return getToDoListElements(tasksByPeriods);
        }
    }

    private Multimap<Period, Task> groupTasksByPeriods(List<Task> tasksFromDB) {
        Multimap<Period, Task> tasksByPeriods = TreeMultimap.create();
        tasksFromDB.forEach(
                task -> tasksByPeriods.put(task.period, task));
        return tasksByPeriods;
    }

    private List<ToDoListElement> getToDoListElements(Multimap<Period, Task> tasksByPeriods) {
        List<ToDoListElement> resultElements = new ArrayList<>();
        for (Period period : tasksByPeriods.keySet()) {
            resultElements.addAll(createElementsForPeriod(tasksByPeriods, period));
        }
        return resultElements;
    }

    private List<ToDoListElement> createElementsForPeriod(Multimap<Period, Task> tasksByPeriods, Period period) {
        List<ToDoListElement> resultElements = new ArrayList<>();
        resultElements.add(HeaderElement.getForPeriod(period));
        resultElements.addAll(createTasks(tasksByPeriods, period));
        resultElements.add(EncourageElement.getByPeriod(period));
        return resultElements;
    }

    private List<TaskItemElement> createTasks(Multimap<Period, Task> tasksByPeriods, Period period) {
        return tasksByPeriods.get(period).stream()
                .map(this::createItemDependingOnMode)
                .collect(Collectors.toList());
    }

    private TaskItemElement createItemDependingOnMode(Task task) {
        if (mode == ToDoListAdapter.Mode.AT_MOST_ONE_ITEM_IS_IN_EDIT_MODE) {
            return new TaskItemElement(task, TaskItemElement.VISIBLE_ITEM_VIEW_TYPE);
        } else {
            return new TaskItemElement(task, TaskItemElement.EDITABLE_ITEM_VIEW_TYPE);
        }
    }

    public List<ToDoListElement> fixDtoList(List<ToDoListElement> listBeforeFix) {
        return listBeforeFix.stream()
                .map(this::fixTypeIfNecessary)
                .collect(Collectors.toList());
    }

    private ToDoListElement fixTypeIfNecessary(ToDoListElement element) {
        if (mode == ToDoListAdapter.Mode.AT_MOST_ONE_ITEM_IS_IN_EDIT_MODE
                && element.getViewType() == ToDoListElement.EDITABLE_ITEM_VIEW_TYPE) {
            return ((TaskItemElement) element).cloneWithViewType(ToDoListElement.VISIBLE_ITEM_VIEW_TYPE);
        } else if (mode == ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE
                && element.getViewType() == ToDoListElement.VISIBLE_ITEM_VIEW_TYPE) {
            return ((TaskItemElement) element).cloneWithViewType(ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        } else return element;
    }

    public List<ToDoListElement> fixElement(List<ToDoListElement> listBeforeFix, TaskItemElement editedElement) {
        Map<Period, List<ToDoListElement>> tasksByPeriods = groupElementsByPeriods(listBeforeFix);
        Period elementOldPeriod = getPeriodOnWhichTheElementIsLocatedOnList(editedElement, tasksByPeriods);
        if (elementOldPeriod == editedElement.getPeriod()) {
            return sameListWithEventuallyAddedWrappingElements(listBeforeFix, editedElement);
        } else {
            return listWithChangedPlaceOfElement(editedElement, tasksByPeriods, elementOldPeriod);
        }
    }

    private Map<Period, List<ToDoListElement>> groupElementsByPeriods(List<ToDoListElement> listElements) {
        Map<Period, List<ToDoListElement>> elementsByPeriods = new TreeMap<>();
        Period currentPeriod = null;
        for (ToDoListElement element : listElements) {
            if (currentPeriod == null || element.getViewType() == ToDoListElement.HEADER_VIEW_TYPE) {
                currentPeriod = element.getPeriod();
                addNewPeriodInMap(elementsByPeriods, element);
            }
            else {
                if (elementsByPeriods.get(currentPeriod) == null) {
                    throw new IllegalStateException("First element of every period should be always HeaderElement but is " + element.getClass());
                }
                elementsByPeriods.get(currentPeriod).add(element);
            }
        }
        return elementsByPeriods;
    }

    private void addNewPeriodInMap(Map<Period, List<ToDoListElement>> elementsByPeriods, ToDoListElement element) {
        List<ToDoListElement> listForNewPeriod = new ArrayList<>();
        listForNewPeriod.add(element);
        elementsByPeriods.put(element.getPeriod(), listForNewPeriod);
    }

    private Period getPeriodOnWhichTheElementIsLocatedOnList(ToDoListElement editedElement, Map<Period, List<ToDoListElement>> tasksByPeriods) {
        for (Period period : tasksByPeriods.keySet()) {
            if (tasksByPeriods.get(period).contains(editedElement)) {
                return period;
            }
        }
        throw new IllegalStateException("Element " + editedElement + " is not on the list");
    }

    private List<ToDoListElement> sameListWithEventuallyAddedWrappingElements(List<ToDoListElement> listBeforeFix, TaskItemElement editedElement) {
        if (listBeforeFix.size() == 1) {
            return createNewPeriodForElement(editedElement);
        }
        return listBeforeFix;
    }

    private List<ToDoListElement> listWithChangedPlaceOfElement(TaskItemElement editedElement, Map<Period, List<ToDoListElement>> tasksByPeriods, Period elementOldPeriod) {
        removeOutdatedElements(editedElement, tasksByPeriods, elementOldPeriod);
        if (tasksByPeriods.containsKey(editedElement.getPeriod())) {
            addElementToaAlreadyExistingPeriod(editedElement, tasksByPeriods);
        } else {
            tasksByPeriods.put(editedElement.getPeriod(), createNewPeriodForElement(editedElement));
        }
        return tasksByPeriods.values().stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private void removeOutdatedElements(TaskItemElement editedElement, Map<Period, List<ToDoListElement>> tasksByPeriods, Period elementOldPeriod) {
        Collection<ToDoListElement> elementsUnderOldPeriod = tasksByPeriods.get(elementOldPeriod);
        elementsUnderOldPeriod.remove(editedElement);
        if (elementsUnderOldPeriod.size() == 2) {
            elementsUnderOldPeriod.clear();
        }
    }

    private void addElementToaAlreadyExistingPeriod(TaskItemElement editedElement, Map<Period, List<ToDoListElement>> tasksByPeriods) {
        List<ToDoListElement> alreadyExistingElementsForPeriod = tasksByPeriods.get(editedElement.getPeriod());
        alreadyExistingElementsForPeriod.add(alreadyExistingElementsForPeriod.size() - 1, editedElement);
    }

    private List<ToDoListElement> createNewPeriodForElement(TaskItemElement editedElement) {
        List<ToDoListElement> newPeriodList = new ArrayList<>();
        newPeriodList.add(HeaderElement.getForPeriod(editedElement.getPeriod()));
        newPeriodList.add(editedElement);
        newPeriodList.add(EncourageElement.getByPeriod(editedElement.getPeriod()));
        return newPeriodList;
    }
}