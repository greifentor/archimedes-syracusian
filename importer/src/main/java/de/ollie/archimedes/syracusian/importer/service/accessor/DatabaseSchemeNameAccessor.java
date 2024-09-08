package de.ollie.archimedes.syracusian.importer.service.accessor;

import java.sql.Connection;

public interface DatabaseSchemeNameAccessor {
	String getDatabaseSchemeName(Connection connection) throws Exception;
}
