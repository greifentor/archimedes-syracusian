package de.ollie.archimedes.syracusian.importer.core.service;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;

public interface DatabaseSchemeImporterService {
	DatabaseSchemeMDO readDatabaseScheme(String schemeName, JDBCConnectionData jdbcConnectionData);
}
