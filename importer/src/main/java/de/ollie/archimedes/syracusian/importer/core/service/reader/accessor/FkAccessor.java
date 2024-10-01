package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor;

import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import java.sql.Connection;
import java.util.Set;

public interface FkAccessor {
	DatabaseType getDatabaseType();

	Set<ForeignKeyMDO> getFks(String schemeName, String tableName, Connection connection);
}
