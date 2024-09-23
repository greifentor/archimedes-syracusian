package de.ollie.archimedes.syracusian.importer.core.service.reader;

import de.ollie.archimedes.syracusian.importer.core.model.ColumnMDO;
import java.sql.Connection;
import java.util.Set;

public interface ColumnReaderService {
	Set<ColumnMDO> read(String schemeName, String tableName, Connection connection);
}
