package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor;

import de.ollie.archimedes.syracusian.importer.core.model.IndexMDO;
import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import java.sql.Connection;
import java.util.Set;

public interface IndicesAccessor extends Accessor {
	Set<IndexMDO> getIndices(String schemeName, TableMDO table, Connection connection);
}
