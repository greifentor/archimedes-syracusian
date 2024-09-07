package de.ollie.archimedes.syracusian.importer;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application starter class (service only).
 */
@Generated
@SpringBootApplication
@ComponentScan("de.ollie.archimedes.syracusian")
public class ArchimedesSyracusianImporterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchimedesSyracusianImporterApplication.class, args);
	}
}
