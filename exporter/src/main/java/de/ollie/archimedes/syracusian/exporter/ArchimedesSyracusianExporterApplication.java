package de.ollie.archimedes.syracusian.exporter;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application starter class (exporter).
 */
@Generated
@SpringBootApplication
@ComponentScan("de.ollie.archimedes.syracusian")
public class ArchimedesSyracusianExporterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArchimedesSyracusianExporterApplication.class, args);
	}
}
