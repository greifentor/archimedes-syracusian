package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseConnectionFactory;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseSchemeImporterService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.ColumnReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.DatabaseSchemeReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.FkReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.IndicesReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.PkReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.TableReaderService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.UniqueConstraintsReaderService;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.SQLException;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class DatabaseSchemeImporterServiceImpl implements DatabaseSchemeImporterService {

	private final ColumnReaderService columnReaderService;
	private final DatabaseConnectionFactory databaseConnectionFactory;
	private final DatabaseSchemeReaderService databaseSchemeReaderService;
	private final FkReaderService fkReaderService;
	private final IndicesReaderService indicesReaderService;
	private final PkReaderService pkReaderService;
	private final TableReaderService tableReaderService;
	private final UniqueConstraintsReaderService uniqueConstaintsReaderService;

	@Override
	public DatabaseSchemeMDO readDatabaseScheme(String schemeName, JDBCConnectionData jdbcConnectionData) {
		ensure(jdbcConnectionData != null, "jdbc connection data cannot be null!");
		ensure(schemeName != null, "scheme name cannot be null!");
		try {
			Connection connection = databaseConnectionFactory.create(jdbcConnectionData);
			DatabaseSchemeMDO scheme = databaseSchemeReaderService.read(connection);
			scheme.setTables(tableReaderService.read(schemeName, connection));
			scheme
				.getTables()
				.forEach(table -> {
					table.setColumns(columnReaderService.read(scheme.getName(), table.getName(), connection));
					table.setPrimaryKey(pkReaderService.read(scheme.getName(), table.getName(), connection));
					table.setForeignKeys(fkReaderService.read(scheme.getName(), table.getName(), connection));
					table.setIndices(indicesReaderService.read(scheme.getName(), table, connection));
					table.setUniqueConstraints(uniqueConstaintsReaderService.read(scheme.getName(), table, connection));
				});
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
