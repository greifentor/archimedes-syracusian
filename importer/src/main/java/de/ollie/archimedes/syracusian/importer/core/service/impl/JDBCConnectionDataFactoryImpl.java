package de.ollie.archimedes.syracusian.importer.core.service.impl;

import de.ollie.archimedes.syracusian.importer.core.service.JDBCConnectionDataFactory;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import jakarta.inject.Named;

@Named
public class JDBCConnectionDataFactoryImpl implements JDBCConnectionDataFactory {

	@Override
	public JDBCConnectionData create(
		String driverClassName,
		String databaseUrl,
		String databaseUserName,
		String databaseUserPassword
	) {
		return new JDBCConnectionData(driverClassName, databaseUrl, databaseUserName, databaseUserPassword);
	}
}
