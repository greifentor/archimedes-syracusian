package de.ollie.archimedes.syracusian.importer;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Named;

@Named
public class DevStarter {

	@PostConstruct
	void postConstruct() {
		System.out.println("\n\nHello!\n\n");
	}
}
