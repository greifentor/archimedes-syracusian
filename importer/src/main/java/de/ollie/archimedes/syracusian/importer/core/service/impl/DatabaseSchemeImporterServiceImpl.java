package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseConnectionFactory;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseSchemeImporterService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.DatabaseSchemeReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.TableReaderService;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class DatabaseSchemeImporterServiceImpl implements DatabaseSchemeImporterService {

	private final DatabaseConnectionFactory databaseConnectionFactory;
	private final DatabaseSchemeReaderService databaseSchemeReaderService;
	private final TableReaderService tableReaderService;

	@Override
	public DatabaseSchemeMDO readDatabaseScheme(String schemeName, JDBCConnectionData jdbcConnectionData) {
		ensure(jdbcConnectionData != null, "jdbc connection data cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		try {
			Connection connection = databaseConnectionFactory.create(jdbcConnectionData);
			DatabaseSchemeMDO scheme = databaseSchemeReaderService.read(connection);
			scheme.setTables(tableReaderService.read(schemeName, connection));
			return scheme;
		} catch (SQLException e) {
			throw new ImportFailureException(
				"failure during database access: " + e.getMessage(),
				ReasonType.CONNECTION_READ_ERROR,
				e
			);
		}
	}
}
