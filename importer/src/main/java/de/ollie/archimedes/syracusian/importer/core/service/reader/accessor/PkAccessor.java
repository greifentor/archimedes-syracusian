package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor;

import java.sql.Connection;
import java.util.Set;

public interface PkAccessor extends Accessor {
	Set<String> getPkColumnNames(String schemeName, String tableName, Connection connection);
}
