package de.ollie.archimedes.syracusian.importer.core.service.impl;

import de.ollie.archimedes.syracusian.importer.core.service.DatabaseConnectionFactory;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import jakarta.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Named
class DatabaseConnectionFactoryImpl implements DatabaseConnectionFactory {

	@Override
	public Connection create(JDBCConnectionData jdbcConnectionData) throws SQLException {
		return DriverManager.getConnection(
			jdbcConnectionData.getDatabaseUrl(),
			jdbcConnectionData.getDatabaseUserName(),
			jdbcConnectionData.getDatabaseUserPassword()
		);
	}
}
