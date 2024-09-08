package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseSchemeImporterService;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseSchemeImporterServiceImpl implements DatabaseSchemeImporterService {

	@Override
	public DatabaseSchemeMDO readDatabaseScheme(String schemeName, JDBCConnectionData jdbcConnectionData) {
		ensure(jdbcConnectionData != null, "jdbc connection data cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		try {
			Connection connection = DriverManager.getConnection(
				jdbcConnectionData.getDatabaseUrl(),
				jdbcConnectionData.getDatabaseUserName(),
				jdbcConnectionData.getDatabaseUserPassword()
			);
			return new DatabaseSchemeMDO(connection.getSchema());
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
}
