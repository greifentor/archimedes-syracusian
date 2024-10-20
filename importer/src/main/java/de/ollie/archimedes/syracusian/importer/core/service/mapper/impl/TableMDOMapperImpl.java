package de.ollie.archimedes.syracusian.importer.core.service.mapper.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.factory.UUIDFactory;
import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.service.mapper.TableMDOMapper;
import de.ollie.archimedes.syracusian.model.Table;
import jakarta.inject.Named;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class TableMDOMapperImpl implements TableMDOMapper {

	private final UUIDFactory uuidFactory;

	@Override
	public Table toModel(TableMDO mdo) {
		ensure(mdo != null, "mdo cannot be null!");
		return new Table().setId(uuidFactory.create()).setName(mdo.getName());
	}
}
