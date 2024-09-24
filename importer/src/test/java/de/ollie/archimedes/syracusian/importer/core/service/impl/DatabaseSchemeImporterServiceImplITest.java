package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseSchemeImporterService;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import jakarta.inject.Inject;
import java.util.List;
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

	@Test
	void happyRun_tableNames() {
		List<String> expected = List.of("BOOK", "RACK");
		assertEquals(expected, readDatabaseSchemeMDO().getTableNames());
	}

	@Test
	void happyRun_columnNames_forTableBOOK() {
		List<String> expected = List.of("ID", "RACK", "SIGNATURE", "TITLE");
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("BOOK").get().getColumnNames());
	}

	@Test
	void happyRun_columnNames_forTableRACK() {
		List<String> expected = List.of("ID", "TOKEN");
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("RACK").get().getColumnNames());
	}
}
