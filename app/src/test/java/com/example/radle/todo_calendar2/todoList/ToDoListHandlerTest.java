package com.example.radle.todo_calendar2.todoList;

import com.example.radle.todo_calendar2.todoList.entity.Period;
import com.example.radle.todo_calendar2.todoList.entity.Task;
import com.example.radle.todo_calendar2.todoList.view.ToDoListAdapter;
import com.example.radle.todo_calendar2.todoList.view.dto.EncourageElement;
import com.example.radle.todo_calendar2.todoList.view.dto.HeaderElement;
import com.example.radle.todo_calendar2.todoList.view.dto.TaskItemElement;
import com.example.radle.todo_calendar2.todoList.view.dto.ToDoListElement;
import com.example.radle.todo_calendar2.todoList.view.util.ToDoListHandler;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ToDoListHandlerTest {
    ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.AT_MOST_ONE_ITEM_IS_IN_EDIT_MODE);

    @Test
    public void createDtoList_shouldReturnOnlyEncourageElement_whenListIsEmpty() {
        Assertions.assertThat(this.toDoListHandler.createDtoList(Collections.emptyList()))
                .containsOnly(EncourageElement.ENCOURAGE_TO_ADD_FIRST_TASK);
    }

    @Test
    public void createDtoList_shouldReturnHeaderTaskItemAndEncourage_whenListContainsOneElement() {
        Task task = new Task(1, "task1", false, Period.TODAY);
        Assertions.assertThat(this.toDoListHandler.createDtoList(Collections.singletonList(task)))
                .containsExactly(
                        HeaderElement.TODAY_HEADER,
                        new TaskItemElement(1, "task1", Period.TODAY, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
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
                        HeaderElement.TODAY_HEADER,
                        new TaskItemElement(1, "task1", Period.TODAY, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        new TaskItemElement(2, "task2", Period.TODAY, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
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
                        HeaderElement.TOMORROW_HEADER,
                        new TaskItemElement(1, "task1", Period.TOMORROW, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_TOMORROW_TASK,
                        HeaderElement.TOMORROW_HEADER,
                        new TaskItemElement(2, "task2", Period.THIS_YEAR, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
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
                        HeaderElement.THIS_WEEK_HEADER,
                        new TaskItemElement(2, "task2", Period.THIS_WEEK, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK,
                        HeaderElement.NEXT_WEEK_HEADER,
                        new TaskItemElement(1, "task1", Period.NEXT_WEEK, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_NEXT_WEEK_TASK
                );
    }

    @Test
    public void createDtoList_shouldReturnHeadersEditableTaskItemsAndEncouragesGroupedByPeriodsInCorrectOrder_whenEveryItemsInEditModeEnabled() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        List<Task> tasks = Arrays.asList(
                new Task(1, "task1", false, Period.NEXT_WEEK),
                new Task(2, "task2", false, Period.THIS_WEEK),
                new Task(3, "task3", false, Period.NEXT_WEEK),
                new Task(4, "task4", true, Period.SOMETIME));

        Assertions.assertThat(toDoListHandler.createDtoList(tasks))
                .containsExactly(
                        HeaderElement.THIS_WEEK_HEADER,
                        new TaskItemElement(2, "task2", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK,
                        HeaderElement.NEXT_WEEK_HEADER,
                        new TaskItemElement(1, "task1", Period.NEXT_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE),
                        new TaskItemElement(3, "task3", Period.NEXT_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_NEXT_WEEK_TASK,
                        HeaderElement.SOMETIME_HEADER,
                        new TaskItemElement(4, "task4", Period.SOMETIME, true, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE),
                        EncourageElement.ENCOURAGE_TO_ADD_SOMETIME_TASK
                );
    }

    @Test
    public void fixDtoList_shouldReturnSameList_whenElementsListContainsOnlyVisibleItemsAndModeIsAtMostOneEditable() {
        List<ToDoListElement> toDoList = Arrays.asList(
                HeaderElement.THIS_WEEK_HEADER,
                new TaskItemElement(1, "task2", Period.THIS_WEEK, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);
        Assertions.assertThat(toDoListHandler.fixDtoList(toDoList)).containsExactlyElementsOf(toDoList);
    }

    @Test
    public void fixDtoList_shouldReturnListWithVisibleItems_whenElementListContainsEditableItemsAndModeIsAtMostOneEditable() {
        List<ToDoListElement> toDoListBeforeFix = Arrays.asList(
                HeaderElement.THIS_WEEK_HEADER,
                new TaskItemElement(1, "task2", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE),
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);
        List<ToDoListElement> toDoListAfterFix = Arrays.asList(
                HeaderElement.THIS_WEEK_HEADER,
                new TaskItemElement(1, "task2", Period.THIS_WEEK, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);

        Assertions.assertThat(toDoListHandler.fixDtoList(toDoListBeforeFix)).containsExactlyElementsOf(toDoListAfterFix);
    }

    @Test
    public void fixDtoList_shouldReturnSameList_whenElementsListContainsOnlyEditableItemsAndEveryItemsInEditModeEnabled() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        List<ToDoListElement> toDoList = Arrays.asList(
                HeaderElement.THIS_WEEK_HEADER,
                new TaskItemElement(1, "task2", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE),
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);
        Assertions.assertThat(toDoListHandler.fixDtoList(toDoList)).containsExactlyElementsOf(toDoList);
    }

    @Test
    public void fixDtoList_shouldReturnListWithEditableItems_whenElementListContainsVisibleItemsAndEveryItemsInEditModeEnabled() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        List<ToDoListElement> toDoListBeforeFix = Arrays.asList(
                HeaderElement.THIS_WEEK_HEADER,
                new TaskItemElement(1, "task2", Period.THIS_WEEK, false, ToDoListElement.VISIBLE_ITEM_VIEW_TYPE),
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);
        List<ToDoListElement> toDoListAfterFix = Arrays.asList(
                HeaderElement.THIS_WEEK_HEADER,
                new TaskItemElement(1, "task2", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE),
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);

        Assertions.assertThat(toDoListHandler.fixDtoList(toDoListBeforeFix)).containsExactlyElementsOf(toDoListAfterFix);
    }

    @Test (expected = IllegalStateException.class)
    public void fixEditedElement_shouldReturnSameList_whenElementDoesNotExist() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        TaskItemElement notExistingElement = new TaskItemElement(1, "task2", Period.NEXT_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        List<ToDoListElement> toDoList = Arrays.asList(
                HeaderElement.THIS_WEEK_HEADER,
                new TaskItemElement(1, "task2", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE),
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);

        toDoListHandler.fixElement(toDoList, notExistingElement);
    }

    @Test
    public void fixEditedElement_shouldReturnSameList_whenElementNotChangedPeriod() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        TaskItemElement elementThatNotChangedPeriod = new TaskItemElement(1, "task2 not edited", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        List<ToDoListElement> toDoList = Arrays.asList(
                HeaderElement.THIS_WEEK_HEADER,
                elementThatNotChangedPeriod,
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);

        Assertions.assertThat(toDoListHandler.fixElement(toDoList, elementThatNotChangedPeriod))
                .containsExactlyElementsOf(toDoList);
    }

    @Test
    public void fixEditedElement_shouldReturnListWithAddedHeaderAndEncourage_whenEditedElementIsOnly() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        TaskItemElement newElement = new TaskItemElement(1, "task2 edited", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        List<ToDoListElement> listBeforeFix = Collections.singletonList(newElement);
        Assertions.assertThat(toDoListHandler.fixElement(listBeforeFix, newElement))
                .containsExactly(
                        HeaderElement.THIS_WEEK_HEADER,
                        newElement,
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);
    }

    @Test
    public void fixEditedElement_shouldReturnListWithAddedHeaderAndEncourageAndLowerPositionOfEditedElement_whenEditedElementChangedPeriodToNext() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);

        TaskItemElement elementThatChangedPeriodFromThisWeekToNextWeek = new TaskItemElement(1, "task1 edited", Period.NEXT_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement otherTaskForThisWeek = new TaskItemElement(2, "task2", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        HeaderElement nextWeekHeader = new HeaderElement("Next week", Period.NEXT_WEEK);


        List<ToDoListElement> listBeforeFix = Arrays.asList(
                HeaderElement.THIS_WEEK_HEADER,
                elementThatChangedPeriodFromThisWeekToNextWeek,
                otherTaskForThisWeek,
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);

        Assertions.assertThat(toDoListHandler.fixElement(listBeforeFix, elementThatChangedPeriodFromThisWeekToNextWeek))
                .containsExactly(
                        HeaderElement.THIS_WEEK_HEADER,
                        otherTaskForThisWeek,
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK,
                        nextWeekHeader,
                        elementThatChangedPeriodFromThisWeekToNextWeek,
                        EncourageElement.ENCOURAGE_TO_ADD_NEXT_WEEK_TASK);
    }

    @Test
    public void fixEditedElement_shouldReturnListWithAddedHeaderAndEncourageAndShiftUpPositionOfEditedElement_whenEditedElementChangedPeriodToPrevious() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        TaskItemElement taskForThisWeek = new TaskItemElement(1, "task1", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement taskThatChangedPeriodFromThisWeekToTomorrow = new TaskItemElement(2, "task2 edited", Period.TOMORROW, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);

        List<ToDoListElement> listBeforeFix = Arrays.asList(
                HeaderElement.THIS_WEEK_HEADER,
                taskForThisWeek,
                taskThatChangedPeriodFromThisWeekToTomorrow,
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);

        Assertions.assertThat(toDoListHandler.fixElement(listBeforeFix, taskThatChangedPeriodFromThisWeekToTomorrow))
                .containsExactly(
                        HeaderElement.TOMORROW_HEADER,
                        taskThatChangedPeriodFromThisWeekToTomorrow,
                        EncourageElement.ENCOURAGE_TO_ADD_TOMORROW_TASK,
                        HeaderElement.THIS_WEEK_HEADER,
                        taskForThisWeek,
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);
    }

    @Test
    public void fixEditedElement_shouldReturnListWithAddedHeaderAndEncourageAndShiftBelowPositionOfEditedElement_whenEditedElementChangedPeriodToAFewLater() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        TaskItemElement firstTaskForThisWeek = new TaskItemElement(1, "task1", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement taskThatChangedPeriodFromThisWeekToThisMonth = new TaskItemElement(2, "task2 edited", Period.THIS_MONTH, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement lastTaskForThisWeek = new TaskItemElement(3, "task3", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement taskForNextWeek = new TaskItemElement(4, "task4", Period.NEXT_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement taskForNextMonth = new TaskItemElement(5, "task5", Period.NEXT_MONTH, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);

        List<ToDoListElement> listBeforeFix = Arrays.asList(
                HeaderElement.THIS_WEEK_HEADER,
                firstTaskForThisWeek,
                taskThatChangedPeriodFromThisWeekToThisMonth,
                lastTaskForThisWeek,
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK,
                HeaderElement.NEXT_WEEK_HEADER,
                taskForNextWeek,
                EncourageElement.ENCOURAGE_TO_ADD_NEXT_WEEK_TASK,
                HeaderElement.NEXT_MONTH_HEADER,
                taskForNextMonth,
                EncourageElement.ENCOURAGE_TO_ADD_NEXT_MONTH_TASK);

        Assertions.assertThat(toDoListHandler.fixElement(listBeforeFix, taskThatChangedPeriodFromThisWeekToThisMonth))
                .containsExactly(
                        HeaderElement.THIS_WEEK_HEADER,
                        firstTaskForThisWeek,
                        lastTaskForThisWeek,
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK,
                        HeaderElement.NEXT_WEEK_HEADER,
                        taskForNextWeek,
                        EncourageElement.ENCOURAGE_TO_ADD_NEXT_WEEK_TASK,
                        HeaderElement.THIS_MONTH_HEADER,
                        taskThatChangedPeriodFromThisWeekToThisMonth,
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_MONTH_TASK,
                        HeaderElement.NEXT_MONTH_HEADER,
                        taskForNextMonth,
                        EncourageElement.ENCOURAGE_TO_ADD_NEXT_MONTH_TASK );
    }

    @Test
    public void fixEditedElement_shouldReturnListWithAddedHeaderAndEncourageAndShiftUpPositionOfEditedElement_whenEditedElementChangedPeriodToAFewEarlier() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        TaskItemElement taskForToday = new TaskItemElement(1, "task1", Period.TODAY, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement taskForThisWeek = new TaskItemElement(2, "task2", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement firstTaskForNextWeek = new TaskItemElement(3, "task3", Period.NEXT_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement taskThatChangedPeriodFromNextWeekToTomorrow = new TaskItemElement(4, "task4 edited", Period.TOMORROW, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement lastTaskFroNextWeek = new TaskItemElement(5, "task5", Period.NEXT_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);

        List<ToDoListElement> listBeforeFix = Arrays.asList(
                HeaderElement.TODAY_HEADER,
                taskForToday,
                EncourageElement.ENCOURAGE_TO_ADD_TODAY_TASK,
                HeaderElement.THIS_WEEK_HEADER,
                taskForThisWeek,
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK,
                HeaderElement.NEXT_WEEK_HEADER,
                firstTaskForNextWeek,
                taskThatChangedPeriodFromNextWeekToTomorrow,
                lastTaskFroNextWeek,
                EncourageElement.ENCOURAGE_TO_ADD_NEXT_WEEK_TASK);

        Assertions.assertThat(toDoListHandler.fixElement(listBeforeFix, taskThatChangedPeriodFromNextWeekToTomorrow))
                .containsExactly(
                        HeaderElement.TODAY_HEADER,
                        taskForToday,
                        EncourageElement.ENCOURAGE_TO_ADD_TODAY_TASK,
                        HeaderElement.TOMORROW_HEADER,
                        taskThatChangedPeriodFromNextWeekToTomorrow,
                        EncourageElement.ENCOURAGE_TO_ADD_TOMORROW_TASK,
                        HeaderElement.THIS_WEEK_HEADER,
                        taskForThisWeek,
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK,
                        HeaderElement.NEXT_WEEK_HEADER,
                        firstTaskForNextWeek,
                        lastTaskFroNextWeek,
                        EncourageElement.ENCOURAGE_TO_ADD_NEXT_WEEK_TASK);
    }

    @Test
    public void fixEditedElement_shouldReturnListWithElementAddedInPreviousPeriod_whenElementChangedPeriodAndSomeElementInNewPeriodAlreadyExists() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        TaskItemElement firstTaskForToday = new TaskItemElement(1, "task1", Period.TODAY, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement secondTaskForToday = new TaskItemElement(2, "task2", Period.TODAY, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement taskThatChangedPeriodFromThisWeekToToday = new TaskItemElement(4, "task4 edited", Period.TODAY, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement taskForThisWeek = new TaskItemElement(3, "task3", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);

        List<ToDoListElement> listBeforeFix = Arrays.asList(
                HeaderElement.TODAY_HEADER,
                firstTaskForToday,
                secondTaskForToday,
                EncourageElement.ENCOURAGE_TO_ADD_TODAY_TASK,
                HeaderElement.THIS_WEEK_HEADER,
                taskForThisWeek,
                taskThatChangedPeriodFromThisWeekToToday,
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);

        Assertions.assertThat(toDoListHandler.fixElement(listBeforeFix, taskThatChangedPeriodFromThisWeekToToday))
                .containsExactly(
                        HeaderElement.TODAY_HEADER,
                        firstTaskForToday,
                        secondTaskForToday,
                        taskThatChangedPeriodFromThisWeekToToday,
                        EncourageElement.ENCOURAGE_TO_ADD_TODAY_TASK,
                        HeaderElement.THIS_WEEK_HEADER,
                        taskForThisWeek,
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);
    }

    @Test
    public void fixEditedElement_shouldReturnListWithElementAddedInNextPeriod_whenElementChangedPeriodAndSomeElementInNewPeriodAlreadyExists() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        TaskItemElement taskThatChangedPeriodFromTodayToThisWeek = new TaskItemElement(1, "task1 edited", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement lastTaskForToday = new TaskItemElement(2, "task2", Period.TODAY, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement firstTaskForThisWeek = new TaskItemElement(3, "task3", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement secondTaskForThisWeek = new TaskItemElement(4, "task4", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);

        List<ToDoListElement> listBeforeFix = Arrays.asList(
                HeaderElement.TODAY_HEADER,
                taskThatChangedPeriodFromTodayToThisWeek,
                lastTaskForToday,
                EncourageElement.ENCOURAGE_TO_ADD_TODAY_TASK,
                HeaderElement.THIS_WEEK_HEADER,
                firstTaskForThisWeek,
                secondTaskForThisWeek,
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);


        Assertions.assertThat(toDoListHandler.fixElement(listBeforeFix, taskThatChangedPeriodFromTodayToThisWeek))
                .containsExactly(
                        new HeaderElement("Today", Period.TODAY),
                        lastTaskForToday,
                        EncourageElement.ENCOURAGE_TO_ADD_TODAY_TASK,
                        new HeaderElement("This week", Period.THIS_WEEK),
                        firstTaskForThisWeek,
                        secondTaskForThisWeek,
                        taskThatChangedPeriodFromTodayToThisWeek,
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);
    }

    @Test
    public void fixEditedElement_shouldReturnListWithChangedPositionOfElementAndRemovedItsPreviousHeaderAndEncourage_whenElementWasTheOnlyInItsPeriod() {
        ToDoListHandler toDoListHandler = new ToDoListHandler(ToDoListAdapter.Mode.ALL_ITEMS_ARE_IN_EDIT_MODE);
        TaskItemElement elementThatChangedPeriodFromTodayToThisWeek = new TaskItemElement(1, "task1 edited", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        TaskItemElement taskFromThisWeek = new TaskItemElement(2, "task2", Period.THIS_WEEK, false, ToDoListElement.EDITABLE_ITEM_VIEW_TYPE);
        List<ToDoListElement> listBeforeFix = Arrays.asList(
                HeaderElement.TODAY_HEADER,
                elementThatChangedPeriodFromTodayToThisWeek,
                EncourageElement.ENCOURAGE_TO_ADD_TODAY_TASK,
                HeaderElement.THIS_WEEK_HEADER,
                taskFromThisWeek,
                EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);


        Assertions.assertThat(toDoListHandler.fixElement(listBeforeFix, elementThatChangedPeriodFromTodayToThisWeek))
                .containsExactly(
                        new HeaderElement("This week", Period.THIS_WEEK),
                        taskFromThisWeek,
                        elementThatChangedPeriodFromTodayToThisWeek,
                        EncourageElement.ENCOURAGE_TO_ADD_THIS_WEEK_TASK);
    }
}