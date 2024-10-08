package de.ollie.archimedes.syracusian.importer.core.model;

import java.util.Set;

public interface ColumnProvider<T> {
	T addColumn(String columnName);

	Set<String> getColumnNames();
}
