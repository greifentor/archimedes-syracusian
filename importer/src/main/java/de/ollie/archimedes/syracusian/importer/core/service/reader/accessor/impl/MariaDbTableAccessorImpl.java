package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Named
class MariaDbTableAccessorImpl extends AbstractTableAccessorImpl {

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.MARIA_DB;
	}

	@Override
	protected ResultSet getTableResultSet(String schemeName, Connection connection) throws SQLException {
		return connection.getMetaData().getTables(schemeName, null, "%", new String[] { "TABLE" });
	}
}
