package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.SQLException;

@Named
public class DatabaseTypeServiceImpl implements DatabaseTypeService {

	@Override
	public DatabaseType getDatabaseType(Connection connection) {
		ensure(connection != null, "connection cannot be null!");
		try {
			if (connection.getMetaData().getDriverName().startsWith("org.hsqldb.jdbc")) {
				return DatabaseType.HSQL;
			}
		} catch (SQLException e) {
			throw new ImportFailureException("", ReasonType.DATABASE_TYPE_READ_ERROR, e);
		}
		return DatabaseType.UNSPECIFIED;
	}
}
