package de.ollie.archimedes.syracusian.importer.service;

import de.ollie.archimedes.syracusian.importer.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;

public interface DatabaseSchemeImporterService {
	DatabaseSchemeMDO readDatabaseScheme(String schemeName, JDBCConnectionData jdbcConnectionData);
}
