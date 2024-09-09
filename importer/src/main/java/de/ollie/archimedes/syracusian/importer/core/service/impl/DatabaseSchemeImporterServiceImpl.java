package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseConnectionFactory;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseSchemeImporterService;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import jakarta.inject.Named;
import java.sql.Connection;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class DatabaseSchemeImporterServiceImpl implements DatabaseSchemeImporterService {

	private final DatabaseConnectionFactory databaseConnectionFactory;

	@Override
	public DatabaseSchemeMDO readDatabaseScheme(String schemeName, JDBCConnectionData jdbcConnectionData) {
		ensure(jdbcConnectionData != null, "jdbc connection data cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		try {
			Connection connection = databaseConnectionFactory.create(jdbcConnectionData);
			return new DatabaseSchemeMDO(connection.getSchema());
		} catch (Exception e) {
			throw new ImportFailureException(
				"failure during database access: " + e.getMessage(),
				ReasonType.CONNECTION_READ_ERROR,
				e
			);
		}
	}
}
