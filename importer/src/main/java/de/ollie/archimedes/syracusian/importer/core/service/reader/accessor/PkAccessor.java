package de.ollie.archimedes.syracusian.importer.core.service.reader.accessor;

import de.ollie.archimedes.syracusian.importer.core.model.PrimaryKeyMDO;
import java.sql.Connection;

public interface PkAccessor extends Accessor {
	PrimaryKeyMDO getPk(String schemeName, String tableName, Connection connection);
}
