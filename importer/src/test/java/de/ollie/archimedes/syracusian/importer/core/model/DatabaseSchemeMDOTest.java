package de.ollie.archimedes.syracusian.importer.core.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DatabaseSchemeMDOTest {

	private static final String TABLE_NAME_0 = "table-name-0";
	private static final String TABLE_NAME_1 = "table-name-1";
	private static final String TABLE_NAME_2 = "table-name-2";

	@InjectMocks
	private DatabaseSchemeMDO unitUnderTest;

	@Nested
	class TestsOfMethod_getTableNames {

		@Test
		void returnsAnEmptyList_whenNoTablesDefinedForTheDatabaseScheme() {
			unitUnderTest.setTables(Set.of());
			assertTrue(unitUnderTest.getTableNames().isEmpty());
		}

		@Test
		void returnsAListWithAlphabeticalOrderedTableNames_whenTablesDefinedForTheDatabaseScheme() {
			// Prepare
			Set<TableMDO> s = Set.of(
				new TableMDO().setName(TABLE_NAME_2),
				new TableMDO().setName(TABLE_NAME_0),
				new TableMDO().setName(TABLE_NAME_1)
			);
			unitUnderTest.setTables(s);
			// Run
			List<String> returned = unitUnderTest.getTableNames();
			// Check
			assertEquals(List.of(TABLE_NAME_0, TABLE_NAME_1, TABLE_NAME_2), unitUnderTest.getTableNames());
		}
	}
}
