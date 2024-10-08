package de.ollie.archimedes.syracusian.importer.core.service.reader;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.model.UniqueConstraintMDO;
import java.sql.Connection;
import java.util.Set;

public interface UniqueConstraintsReaderService {
	Set<UniqueConstraintMDO> read(String schemeName, TableMDO table, Connection connection);
}
