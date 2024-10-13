package de.ollie.archimedes.syracusian.importer.core.service;

import de.ollie.archimedes.syracusian.model.JDBCConnectionData;

public interface JDBCConnectionDataFactory {
	JDBCConnectionData create(
		String driverClassName,
		String databaseUrl,
		String databaseUserName,
		String databaseUserPassword
	);
}
