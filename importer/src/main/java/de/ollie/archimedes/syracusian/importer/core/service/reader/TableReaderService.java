package de.ollie.archimedes.syracusian.importer.core.service.reader;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import java.util.Set;

public interface TableReaderService {
	Set<TableMDO> getTables();
}
