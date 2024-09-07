package de.ollie.archimedes.syracusian.importer.service;

import de.ollie.archimedes.syracusian.importer.model.DatabaseScheme;
import de.ollie.archimedes.syracusian.importer.model.JDBCConnectionData;

public interface DatabaseSchemeImporterService {
	DatabaseScheme readDatabaseScheme(String schemeName, JDBCConnectionData jdbcConnectionData);
}
