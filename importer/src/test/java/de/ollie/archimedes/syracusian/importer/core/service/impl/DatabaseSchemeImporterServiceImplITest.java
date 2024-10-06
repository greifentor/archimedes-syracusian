package de.ollie.archimedes.syracusian.importer.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.model.UniqueConstraintMDO;
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
		List<String> expected = List.of("AUTHOR", "AUTHOR_BOOK", "BOOK", "RACK", "ROOM");
		assertEquals(expected, readDatabaseSchemeMDO().getTableNames());
	}

	@Test
	void happyRun_columnNames_forTableBOOK() {
		List<String> expected = List.of("ID", "RACK", "SIGNATURE", "TITLE");
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("BOOK").get().getColumnNames());
	}

	@Test
	void happyRun_columnNames_forTableRACK() {
		List<String> expected = List.of("ID", "ROOM", "TOKEN");
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
	void happyRun_pkColumnNames_forTableAUTHOR_BOOK() {
		List<String> expected = List.of("AUTHOR", "BOOK");
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("AUTHOR_BOOK").get().getPkColumnNames());
	}

	@Test
	void happyRun_pkColumnNames_forTableBOOK() {
		List<String> expected = List.of("ID");
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("BOOK").get().getPkColumnNames());
	}

	@Test
	void happyRun_pkColumnNames_forTableRACK() {
		List<String> expected = List.of("ID");
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("RACK").get().getPkColumnNames());
	}

	@Test
	void happyRun_foreignKeys_forTableBOOK() {
		Set<ForeignKeyMDO> expected = Set.of(
			new ForeignKeyMDO()
				.setColumnName("RACK")
				.setName("FK_BOOK_RACK_TO_RACK")
				.setReferencedColumnName("ID")
				.setReferencedTableName("RACK")
		);
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("BOOK").get().getForeignKeys());
	}

	@Test
	void happyRun_foreignKeys_forTableRACK() {
		Set<ForeignKeyMDO> expected = Set.of(
			new ForeignKeyMDO()
				.setColumnName("ROOM")
				.setName("FK_RACK_ROOM_TO_ROOM")
				.setReferencedColumnName("ID")
				.setReferencedTableName("ROOM")
		);
		assertEquals(expected, readDatabaseSchemeMDO().findTableByName("RACK").get().getForeignKeys());
	}

	@Test
	void happyRun_foreignKeys_forTableROOM() {
		assertEquals(Set.of(), readDatabaseSchemeMDO().findTableByName("ROOM").get().getForeignKeys());
	}

	@Test
	void happyRun_uniques_forTableAUTHOR() {
		assertEquals(
			Set.of(
				new UniqueConstraintMDO()
					.setColumnNames(Set.of("DATE_OF_BIRTH", "FORE_NAME", "LAST_NAME"))
					.setName("SYS_IDX_U_AUTHOR_LAST_NAME_FORE_NAME_DATE_OF_BIRTH_10119")
			),
			readDatabaseSchemeMDO().findTableByName("AUTHOR").get().getUniqueConstraints()
		);
	}

	@Test
	void happyRun_uniques_forTableBOOK() {
		assertEquals(
			Set.of(new UniqueConstraintMDO().setColumnNames(Set.of("SIGNATURE")).setName("SYS_IDX_U_BOOK_SIGNATURE_10098")),
			readDatabaseSchemeMDO().findTableByName("BOOK").get().getUniqueConstraints()
		);
	}

	@Test
	void happyRun_uniques_forTableROOM() {
		assertEquals(
			Set.of(
				new UniqueConstraintMDO().setColumnNames(Set.of("DESCRIPTION")).setName("SYS_IDX_SYS_CT_10107_10112"),
				new UniqueConstraintMDO().setColumnNames(Set.of("TOKEN")).setName("SYS_IDX_SYS_CT_10108_10113")
			),
			readDatabaseSchemeMDO().findTableByName("ROOM").get().getUniqueConstraints()
		);
	}
}
