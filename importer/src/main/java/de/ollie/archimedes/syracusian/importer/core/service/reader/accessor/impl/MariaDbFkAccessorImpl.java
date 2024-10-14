package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
public class MariaDbFkAccessorImpl extends AbstractFkAccessorImpl {

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.MARIA_DB;
	}

	@Override
	protected ResultSet getFksResultSet(String schemeName, String tableName, Connection connection) throws SQLException {
		return connection.getMetaData().getImportedKeys(schemeName, "%", tableName);
	}
}
