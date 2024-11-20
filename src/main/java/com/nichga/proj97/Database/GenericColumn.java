package com.nichga.proj97.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for columns in database tables.
 * Subclasses can define specific columns of the tables they manage.
 */
public abstract class GenericColumn {
    public final int index;
    public final String name;

    protected GenericColumn(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    /**
     * Retrieves a list of column names based on the numeric attribute value (attr).
     *
     * @param columns the list of all available columns.
     * @param attr the numeric attribute value of the columns.
     * @return a list of column names whose index matches the provided attribute.
     */
    public static List<String> getAttributes(List<? extends GenericColumn> columns, long attr) {
        List<String> attributes = new ArrayList<>();
        for (GenericColumn column : columns) {
            if ((column.getIndex() & attr) != 0) {
                attributes.add(column.getName());
            }
        }
        return attributes;
    }

    /**
     * Converts a list of column names into their numeric attribute representation.
     *
     * @param columns the list of all available columns.
     * @param args the names of the columns to convert.
     * @return the numeric attribute value for the specified columns.
     */
    public static long getNumberRepresentation(List<? extends GenericColumn> columns, String... args) {
        long numberRepresentation = 0;
        for (String arg : args) {
            for (GenericColumn column : columns) {
                if (column.getName().equals(arg)) {
                    numberRepresentation += column.getIndex();
                }
            }
        }
        return numberRepresentation;
    }
}
