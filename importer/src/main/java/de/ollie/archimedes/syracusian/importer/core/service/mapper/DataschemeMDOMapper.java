package de.ollie.archimedes.syracusian.importer.core.service.mapper;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.model.Datascheme;

public interface DataschemeMDOMapper {
	Datascheme toModel(DatabaseSchemeMDO mdo);
}
