package de.ollie.archimedes.syracusian.importer.core.service.mapper;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.model.Table;

public interface TableMDOMapper {
	Table toModel(TableMDO mdo);
}
