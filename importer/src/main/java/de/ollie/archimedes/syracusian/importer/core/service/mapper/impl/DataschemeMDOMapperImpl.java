package de.ollie.archimedes.syracusian.importer.core.service.mapper.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.factory.UUIDFactory;
import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.service.mapper.DataschemeMDOMapper;
import de.ollie.archimedes.syracusian.model.Datascheme;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class DataschemeMDOMapperImpl implements DataschemeMDOMapper {

	private final UUIDFactory uuidFactory;

	@Override
	public Datascheme toModel(DatabaseSchemeMDO mdo) {
		ensure(mdo != null, "mdo cannot be null!");
		return new Datascheme().setId(uuidFactory.create()).setName(mdo.getName());
	}
}
