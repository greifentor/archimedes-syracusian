package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
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

	private DatabaseSchemeMDO readDatabaseSchemeMDO() {
		return unitUnderTest.readDatabaseScheme(
			"PUBLIC",
			new JDBCConnectionData(
				"org.hsqldb.jdbc.JDBCDriver",
				"jdbc:hsqldb:file:src/test/resources/test-db/test-db",
				"sa",
				null
			)
		);
	}

	@Test
	void happyRun_schemeName() {
		assertEquals("PUBLIC", readDatabaseSchemeMDO().getName());
	}
	//	@Test
	//	void happyRun_tableNames() {
	//		Set<String> expected = Set.of("BOOK, RACK");
	//		assertEquals(expected, readDatabaseSchemeMDO().getTableNames());
	//	}
}
