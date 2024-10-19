package de.ollie.archimedes.syracusian.importer.core.factory.impl;

import de.ollie.archimedes.syracusian.importer.core.factory.UUIDFactory;
import java.util.UUID;

public class UUIDFactoryImpl implements UUIDFactory {

	@Override
	public UUID create() {
		return UUID.randomUUID();
	}
}
