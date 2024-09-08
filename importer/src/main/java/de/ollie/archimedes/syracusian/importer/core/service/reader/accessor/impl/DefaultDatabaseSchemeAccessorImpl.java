package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.DatabaseSchemeAccessor;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.SQLException;

@Named
public class DefaultDatabaseSchemeAccessorImpl implements DatabaseSchemeAccessor {

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.UNSPECIFIED;
	}

	@Override
	public String getName(Connection connection) {
		ensure(connection != null, "conncetion can not be null!");
		try {
			return connection.getSchema();
		} catch (SQLException e) {
			throw new ImportFailureException("reading schema from connection failed", ReasonType.SCHEME_NAME_READ_ERROR, e);
		}
	}
}
