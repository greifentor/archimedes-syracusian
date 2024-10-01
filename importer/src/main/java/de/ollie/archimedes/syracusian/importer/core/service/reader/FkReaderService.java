package de.ollie.archimedes.syracusian.importer.core.service.reader;

import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
import java.sql.Connection;
import java.util.Set;

public interface FkReaderService {
	Set<ForeignKeyMDO> read(String schemeName, String tableName, Connection connection);
}
