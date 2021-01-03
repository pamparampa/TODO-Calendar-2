package com.example.radle.todo_calendar2.dao;

import android.database.Cursor;
import android.net.Uri;

import com.googlecode.cqengine.ConcurrentIndexedCollection;
import com.googlecode.cqengine.query.parser.sql.SQLParser;
import com.googlecode.cqengine.resultset.ResultSet;

import java.util.List;
import java.util.function.Function;

import static com.googlecode.cqengine.codegen.AttributeBytecodeGenerator.createAttributes;
import static com.googlecode.cqengine.codegen.MemberFilters.METHODS_ONLY;

public class ContentResolverStub<T> extends ContentResolverProxy {
    private final Class<T> pojoClass;

    private final Function<Iterable<T>, Cursor> cursorConverter;

    public static <T> ContentResolverStub<T> of(final Class<T> type,
                                                final Function<Iterable<T>, Cursor> cursorConverter) {
        return new ContentResolverStub<>(type, cursorConverter);
    }

    private ContentResolverStub(final Class<T> type,
                                final Function<Iterable<T>, Cursor> cursorConverter) {
        super(null);
        this.pojoClass = type;
        this.cursorConverter = cursorConverter;
    }

    private final ConcurrentIndexedCollection<T> data =
            new ConcurrentIndexedCollection<>();

    void setData(final List<T> data) {
        this.data.addAll(data);
    }


    @Override
    Cursor query(final Uri uri, final String[] eventProjection, final String selection,
                 final String[] args, final String order) {
        final SQLParser<T> parser = SQLParser.forPojoWithAttributes(this.pojoClass,
                createAttributes(this.pojoClass, METHODS_ONLY));
        final ResultSet<T> resultSet =
                parser.retrieve(this.data, prepareQuery(prepareSelection(selection), args));

        return this.cursorConverter.apply(resultSet);
    }

    private String prepareSelection(final String selection) {
        return "SELECT * FROM data WHERE (" + selection.replace("?", "%s") + ")";
    }

    private String prepareQuery(final String selection, final String... args) {
        return String.format(selection, args);
    }
}
