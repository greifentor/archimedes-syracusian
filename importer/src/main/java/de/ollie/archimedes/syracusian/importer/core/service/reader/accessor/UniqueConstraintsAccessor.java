package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor;

import de.ollie.archimedes.syracusian.importer.core.model.UniqueConstraintMDO;
import java.sql.Connection;
import java.util.Set;

public interface UniqueConstraintsAccessor extends Accessor {
	Set<UniqueConstraintMDO> getUniqueConstraints(String schemeName, String tableName, Connection connection);
}
