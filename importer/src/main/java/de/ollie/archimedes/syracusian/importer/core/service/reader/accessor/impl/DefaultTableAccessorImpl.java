package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.TableAccessor;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.inject.Named;
import java.sql.Connection;
import java.util.Set;

@Named
class DefaultTableAccessorImpl implements TableAccessor {

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.UNSPECIFIED;
	}

	@Override
	public Set<TableMDO> getTables(String schemeName, Connection connection) {
		return Set.of(); // TODO: Continue here !!!
	}
}
