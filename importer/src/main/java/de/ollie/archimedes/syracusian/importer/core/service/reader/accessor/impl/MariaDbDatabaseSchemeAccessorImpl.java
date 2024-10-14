package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.DatabaseSchemeAccessor;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.inject.Named;
import java.sql.Connection;

@Named
public class MariaDbDatabaseSchemeAccessorImpl implements DatabaseSchemeAccessor {

	@Override
	public DatabaseType getDatabaseType() {
		return DatabaseType.MARIA_DB;
	}

	@Override
	public String getName(String schemeName, Connection connection) {
		ensure(connection != null, "connection can not be null!");
		ensure(schemeName != null, "scheme name can not be null!");
		return schemeName;
	}
}
