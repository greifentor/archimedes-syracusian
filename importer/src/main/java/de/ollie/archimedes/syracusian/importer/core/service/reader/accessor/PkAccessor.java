package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor;

import de.ollie.archimedes.syracusian.model.DatabaseType;
import java.sql.Connection;
import java.util.Set;

public interface PkAccessor {
	DatabaseType getDatabaseType();

	Set<String> getPkColumnNames(String schemeName, String tableName, Connection connection);
}
