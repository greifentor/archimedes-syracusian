package de.ollie.archimedes.syracusian.importer.core.service.reader;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import java.sql.Connection;
import java.util.Set;

public interface TableReaderService {
	Set<TableMDO> read(String schemeName, Connection connection);
}
