package com.example.radle.todo_calendar2.dao;

import org.apache.commons.lang.StringUtils;

import java.util.List;

class QueryBracketsHelper {
    String buildBracketsWithPlaceholders(final List<String> elements) {
        return "("
                + StringUtils.join(elements.stream().map(any -> "?").toArray(), ",")
                + ")";
    }
}
