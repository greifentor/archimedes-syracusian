package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor;

import de.ollie.archimedes.syracusian.model.DatabaseType;
import java.sql.Connection;

public interface DatabaseSchemeAccessor {
	DatabaseType getDatabaseType();

	String getName(Connection connection);
}
