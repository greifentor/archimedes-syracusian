package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import java.sql.Connection;
import java.util.Set;

public interface TableAccessor {
	DatabaseType getDatabaseType();

	Set<TableMDO> getTables(String schemeName, Connection connection);
}
