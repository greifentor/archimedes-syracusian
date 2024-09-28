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
class TableMDOTest {

	private static final String COLUMN_NAME_0 = "column-0";
	private static final String COLUMN_NAME_1 = "column-1";
	private static final String COLUMN_NAME_2 = "column-2";

	@InjectMocks
	private TableMDO unitUnderTest;

	@Nested
	class TestsOfMethod_getColumnNames {

		@Test
		void returnsAnEmptyList_whenTableHasNoColumns() {
			assertTrue(unitUnderTest.getColumnNames().isEmpty());
		}

		@Test
		void returnsASortedListWithTheColumnNames_whenTableColumns() {
			// Prepare
			Set<ColumnMDO> columns = Set.of(
				new ColumnMDO().setName(COLUMN_NAME_0),
				new ColumnMDO().setName(COLUMN_NAME_2),
				new ColumnMDO().setName(COLUMN_NAME_1)
			);
			unitUnderTest.setColumns(columns);
			// Run
			List<String> returned = unitUnderTest.getColumnNames();
			// Check
			assertEquals(List.of(COLUMN_NAME_0, COLUMN_NAME_1, COLUMN_NAME_2), returned);
		}
	}

	@Nested
	class TestsOfMethod_getNotNullColumnNames {

		@Test
		void returnsAnEmptyList_whenTableHasNoColumns() {
			assertTrue(unitUnderTest.getColumnNames().isEmpty());
		}

		@Test
		void returnsASortedListWithTheColumnNames_whenTableColumns() {
			// Prepare
			Set<ColumnMDO> columns = Set.of(
				new ColumnMDO().setName(COLUMN_NAME_0).setNullable(true),
				new ColumnMDO().setName(COLUMN_NAME_2).setNullable(false),
				new ColumnMDO().setName(COLUMN_NAME_1).setNullable(false)
			);
			unitUnderTest.setColumns(columns);
			// Run
			List<String> returned = unitUnderTest.getNotNullColumnNames();
			// Check
			assertEquals(List.of(COLUMN_NAME_1, COLUMN_NAME_2), returned);
		}
	}
}
