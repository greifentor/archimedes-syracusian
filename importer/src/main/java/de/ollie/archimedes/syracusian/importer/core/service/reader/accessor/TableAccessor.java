package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import java.sql.Connection;
import java.util.Set;

public interface TableAccessor extends Accessor {
	Set<TableMDO> getTables(String schemeName, Connection connection);
}
