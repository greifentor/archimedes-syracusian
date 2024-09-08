package de.ollie.archimedes.syracusian.importer.core.service;

import de.ollie.archimedes.syracusian.model.DatabaseType;
import java.sql.Connection;

public interface DatabaseTypeService {
	DatabaseType getDatabaseType(Connection connection);
}
