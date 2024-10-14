package de.ollie.archimedes.syracusian.importer.core.service.reader;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import java.sql.Connection;

public interface DatabaseSchemeReaderService {
	DatabaseSchemeMDO read(String schemeName, Connection connection);
}
