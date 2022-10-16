package com.example.radle.todo_calendar2.todoList;

import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.entity.Task;
import com.example.radle.todo_calendar2.todoList.view.dto.EncourageElement;
import com.example.radle.todo_calendar2.todoList.view.dto.HeaderElement;
import com.example.radle.todo_calendar2.todoList.view.dto.TaskItemElement;
import com.example.radle.todo_calendar2.todoList.view.dto.ToDoListElement;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ToDoListHandler {


    public List<ToDoListElement> createDtoList(List<Task> tasksFromDB) {
        if (tasksFromDB.isEmpty()) {
            return Collections.singletonList(EncourageElement.ENCOURAGE_TO_ADD_FIRST_TASK);
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
        resultElements.add(new HeaderElement(period.getName()));
        resultElements.addAll(createTasks(tasksByPeriods, period));
        resultElements.add(EncourageElement.getByPeriod(period));
        return resultElements;
    }

    private List<TaskItemElement> createTasks(Multimap<Period, Task> tasksByPeriods, Period period) {
        return tasksByPeriods.get(period).stream()
                .map(TaskItemElement::new)
                .collect(Collectors.toList());
    }
}
