package de.ollie.archimedes.syracusian.importer.core.service.reader;

import de.ollie.archimedes.syracusian.importer.core.model.PrimaryKeyMDO;
import java.sql.Connection;

public interface PkReaderService {
	PrimaryKeyMDO read(String schemeName, String tableName, Connection connection);
}
