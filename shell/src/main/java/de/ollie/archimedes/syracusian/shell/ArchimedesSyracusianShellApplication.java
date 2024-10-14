package de.ollie.archimedes.syracusian.shell;

import lombok.Generated;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application starter class (shell only).
 */
@Generated
@SpringBootApplication
@ComponentScan("de.ollie.archimedes.syracusian")
public class ArchimedesSyracusianShellApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ArchimedesSyracusianShellApplication.class).headless(false).run(args);
	}
}
