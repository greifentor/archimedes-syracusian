package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseSchemeImporterService;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import jakarta.inject.Inject;
import java.util.List;
import java.util.Set;
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

	@Test
	void happyRun_notNullColumnNames_forTableBOOK() {
		List<String> expected = List.of("ID", "SIGNATURE", "TITLE");
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("BOOK").get().getNotNullColumnNames());
	}

	@Test
	void happyRun_notNullColumnNames_forTableRACK() {
		List<String> expected = List.of("ID", "TOKEN");
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("RACK").get().getNotNullColumnNames());
	}

	@Test
	void happyRun_pkColumnNames_forTableBOOK() {
		List<String> expected = List.of("ID");
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("BOOK").get().getPKColumnNames());
	}

	@Test
	void happyRun_pkColumnNames_forTableRACK() {
		List<String> expected = List.of("ID");
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("RACK").get().getPKColumnNames());
	}

	@Test
	void happyRun_foreignKeys_forTableBOOK() {
		Set<ForeignKeyMDO> expected = Set.of(
			new ForeignKeyMDO()
				.setColumnName("RACK")
				.setName("SYS_FK_10103")
				.setReferencedColumnName("ID")
				.setReferencedTableName("RACK")
		);
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("BOOK").get().getForeignKeys());
	}

	@Test
	void happyRun_foreignKeys_forTableRACK() {
		assertEquals(Set.of(), readDatabaseSchemeMDO().findTableByName("RACK").get().getForeignKeys());
	}
}
