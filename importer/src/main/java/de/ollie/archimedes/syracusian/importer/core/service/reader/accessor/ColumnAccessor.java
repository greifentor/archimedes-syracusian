package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor;

import de.ollie.archimedes.syracusian.importer.core.model.ColumnMDO;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import java.sql.Connection;
import java.util.Set;

public interface ColumnAccessor {
	DatabaseType getDatabaseType();

	Set<ColumnMDO> getColumns(String schemeName, String tableName, Connection connection);
}
