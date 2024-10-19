package de.ollie.archimedes.syracusian.importer.core.factory.impl;

import de.ollie.archimedes.syracusian.importer.core.factory.UUIDFactory;
import jakarta.inject.Named;
import java.util.UUID;

@Named
public class UUIDFactoryImpl implements UUIDFactory {

	@Override
	public UUID create() {
		return UUID.randomUUID();
	}
}
