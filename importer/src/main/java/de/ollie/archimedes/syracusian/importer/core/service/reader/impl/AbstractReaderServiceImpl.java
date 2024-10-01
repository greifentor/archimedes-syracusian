package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.Accessor;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AbstractReaderServiceImpl<A extends Accessor> {

	protected final DatabaseTypeService databaseTypeService;
	private final List<A> accessorsList;

	protected final Map<DatabaseType, A> accessors = new HashMap<>();
	protected A defaultAccessor;

	@PostConstruct
	void postConstruct() {
		accessorsList.stream().forEach(a -> accessors.put(a.getDatabaseType(), a));
		ensure(
			accessors.containsKey(DatabaseType.UNSPECIFIED),
			new IllegalStateException("FkAccessor implementation for UNSPECIFIED missed!")
		);
		defaultAccessor = accessors.get(DatabaseType.UNSPECIFIED);
	}
}
