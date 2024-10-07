package de.ollie.archimedes.syracusian.importer.core.service.reader;

import de.ollie.archimedes.syracusian.importer.core.model.IndexMDO;
import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import java.sql.Connection;
import java.util.Set;

public interface IndicesReaderService {
	Set<IndexMDO> read(String schemeName, TableMDO table, Connection connection);
}
