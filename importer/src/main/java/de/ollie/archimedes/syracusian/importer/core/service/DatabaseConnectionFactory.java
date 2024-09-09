package de.ollie.archimedes.syracusian.importer.core.service;

import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseConnectionFactory {
	Connection create(JDBCConnectionData jdbcConnectionData) throws SQLException;
}
