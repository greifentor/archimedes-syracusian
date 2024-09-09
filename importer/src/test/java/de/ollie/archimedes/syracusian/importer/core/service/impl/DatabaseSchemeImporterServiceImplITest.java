package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.archimedes.syracusian.importer.core.service.DatabaseSchemeImporterService;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class DatabaseSchemeImporterServiceImplITest {

	@Inject
	private DatabaseSchemeImporterService unitUnderTest;

	@Test
	void happyRun() {
		assertEquals(
			"PUBLIC",
			unitUnderTest
				.readDatabaseScheme(
					"PUBLIC",
					new JDBCConnectionData(
						"org.hsqldb.jdbc.JDBCDriver",
						"jdbc:hsqldb:file:src/test/resources/test-db/test-db",
						"sa",
						null
					)
				)
				.getName()
		);
	}
}
