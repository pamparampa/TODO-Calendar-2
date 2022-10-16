package com.example.radle.todo_calendar2.todoList;

import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.entity.Task;
import com.example.radle.todo_calendar2.todoList.view.dto.EncourageElement;
import com.example.radle.todo_calendar2.todoList.view.dto.HeaderElement;
import com.example.radle.todo_calendar2.todoList.view.dto.TaskItemElement;
import com.example.radle.todo_calendar2.todoList.view.dto.ToDoListElement;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ToDoListHandlerTest {
    ToDoListHandler toDoListHandler = new ToDoListHandler();

    @Test
    public void createDtoList_shouldReturnOnlyEncourageElement_whenListIsEmpty() {
        Assertions.assertThat(this.toDoListHandler.createDtoList(Collections.emptyList()))
                .containsOnly(EncourageElement.ENCOURAGE_TO_ADD_FIRST_TASK);
    }

    @Test
    public void createDtoList_shouldReturnHeaderTaskItemAndEncourage_whenListContainsOneElement() {
        Task task = new Task("task1", false, Period.TODAY);
        Assertions.assertThat(this.toDoListHandler.createDtoList(Collections.singletonList(task)))
                .containsExactly(
                        new HeaderElement("Today"),
                        new TaskItemElement("task1", Period.TODAY, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_TODAY_TASK
                );
    }

    @Test
    public void createDtoList_shouldReturnHeaderTaskItemsAndEncourage_whenListContainsFewElementsForOnePeriod() {
        List<Task> tasks = Arrays.asList(
                new Task(1, "task1", false, Period.TODAY),
                new Task(2, "task2", false, Period.TODAY));
        Assertions.assertThat(this.toDoListHandler.createDtoList(tasks))
                .containsExactly(
                        new HeaderElement("Today"),
                        new TaskItemElement("task1", Period.TODAY, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        new TaskItemElement("task2", Period.TODAY, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_TODAY_TASK
                );
    }

    @Test
    public void createDtoList_shouldReturnHeadersTaskItemsAndEncouragesGroupedByPeriods_whenListContainsFewElementsForFewPeriods() {
        List<Task> tasks = Arrays.asList(
                new Task(1, "task1", false, Period.TOMORROW),
                new Task(2, "task2", false, Period.THIS_YEAR));
        Assertions.assertThat(this.toDoListHandler.createDtoList(tasks))
                .containsExactly(
                        new HeaderElement("Tomorrow"),
                        new TaskItemElement("task1", Period.TOMORROW, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_TOMORROW_TASK,
                        new HeaderElement("This year"),
                        new TaskItemElement("task2", Period.THIS_YEAR, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_YEAR_TASK
                );
    }

    @Test
    public void createDtoList_shouldReturnHeadersTaskItemsAndEncouragesGroupedByPeriodsInCorrectOrder_whenListContainsFewElementsForFewPeriodsInStrangeOrder() {
        List<Task> tasks = Arrays.asList(
                new Task(1, "task1", false, Period.NEXT_WEEK),
                new Task(2, "task2", false, Period.THIS_WEEK));
        Assertions.assertThat(this.toDoListHandler.createDtoList(tasks))
                .containsExactly(
                        new HeaderElement("This week"),
                        new TaskItemElement("task2", Period.THIS_WEEK, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK,
                        new HeaderElement("Next week"),
                        new TaskItemElement("task1", Period.NEXT_WEEK, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_NEXT_WEEK_TASK
                );
    }

    @Test
    public void createDtoList_shouldReturnHeadersTaskItemsAndEncouragesGroupedByPeriodsInCorrectOrder() {
        List<Task> tasks = Arrays.asList(
                new Task(1, "task1", false, Period.NEXT_WEEK),
                new Task(2, "task2", false, Period.THIS_WEEK),
                new Task(3, "task3", false, Period.NEXT_WEEK),
                new Task(4, "task4", true, Period.SOMETIME));

        Assertions.assertThat(this.toDoListHandler.createDtoList(tasks))
                .containsExactly(
                        new HeaderElement("This week"),
                        new TaskItemElement("task2", Period.THIS_WEEK, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK,
                        new HeaderElement("Next week"),
                        new TaskItemElement("task1", Period.NEXT_WEEK, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        new TaskItemElement("task3", Period.NEXT_WEEK, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_NEXT_WEEK_TASK,
                        new HeaderElement("Sometime"),
                        new TaskItemElement("task4", Period.SOMETIME, true, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_SOMETIME_TASK
                );
    }
}