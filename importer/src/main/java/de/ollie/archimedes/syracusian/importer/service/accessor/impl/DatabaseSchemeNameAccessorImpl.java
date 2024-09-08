package de.ollie.archimedes.syracusian.importer.service.accessor.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.service.accessor.DatabaseSchemeNameAccessor;
import jakarta.inject.Named;
import java.sql.Connection;

@Named
public class DatabaseSchemeNameAccessorImpl implements DatabaseSchemeNameAccessor {

	@Override
	public String getDatabaseSchemeName(Connection connection) throws Exception {
		ensure(connection != null, "conncetion can not be null!");
		return connection.getSchema();
	}
}
