package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.model.ForeignKeyMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.FkAccessor;
import de.ollie.archimedes.syracusian.model.DatabaseType;
import java.sql.Connection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class FkReaderServiceImplTest {

	private static final String SCHEME_NAME = "scheme-name";
	private static final String TABLE_NAME = "table-name";

	@Mock
	private Connection connection;

	@Mock
	private DatabaseTypeService databaseTypeService;

	@Mock
	private FkAccessor accessor0;

	@Mock
	private FkAccessor accessor1;

	@Mock
	private ForeignKeyMDO foreignKey0;

	@Mock
	private List<FkAccessor> accessors;

	@InjectMocks
	private FkReaderServiceImpl unitUnderTest;

	@Nested
	class TestsOfMethod_postConstruct {

		@Test
		void throwsAnException_whenNoAccessorIsDefined() {
			assertThrows(IllegalStateException.class, () -> unitUnderTest.postConstruct());
		}

		@Test
		void throwsAnException_whenNoAccessorIsDefinedForDatabaseTypeUNSPECIFIED() {
			// Prepare
			when(accessor0.getDatabaseType()).thenReturn(DatabaseType.HSQL);
			when(accessors.stream()).thenReturn(Stream.of(accessor0));
			// Run & Check
			assertThrows(IllegalStateException.class, () -> unitUnderTest.postConstruct());
		}
	}

	@Nested
	class TestsOfMethod_read_String_String_Connection {

		@Test
		void throwsAnException_passingConnectionAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.read(SCHEME_NAME, TABLE_NAME, null));
		}

		@Test
		void throwsAnException_passingSchemeNameAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.read(null, TABLE_NAME, connection));
		}

		@Test
		void throwsAnException_passingTableNameAsNullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.read(SCHEME_NAME, null, connection));
		}

		@Test
		void returnsASetWithCorrectForeignKey_whenAnAccessorIsDefinedForDatabaseTypeUNSPECIFIEDOnly() {
			// Prepare
			Set<ForeignKeyMDO> expected = Set.of(foreignKey0);
			when(accessor0.getDatabaseType()).thenReturn(DatabaseType.UNSPECIFIED);
			when(accessor0.getFks(SCHEME_NAME, TABLE_NAME, connection)).thenReturn(expected);
			when(accessors.stream()).thenReturn(Stream.of(accessor0));
			when(databaseTypeService.getDatabaseType(connection)).thenReturn(DatabaseType.HSQL);
			unitUnderTest.postConstruct();
			// Run
			Set<ForeignKeyMDO> returned = unitUnderTest.read(SCHEME_NAME, TABLE_NAME, connection);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void returnsASetWithCorrectForeignKey_whenAnAccessorIsDefinedForSpecifiedDatabaseType() {
			// Prepare
			Set<ForeignKeyMDO> expected = Set.of(foreignKey0);
			when(accessor0.getDatabaseType()).thenReturn(DatabaseType.UNSPECIFIED);
			when(accessor1.getDatabaseType()).thenReturn(DatabaseType.HSQL);
			when(accessor1.getFks(SCHEME_NAME, TABLE_NAME, connection)).thenReturn(expected);
			when(accessors.stream()).thenReturn(Stream.of(accessor0, accessor1));
			when(databaseTypeService.getDatabaseType(connection)).thenReturn(DatabaseType.HSQL);
			unitUnderTest.postConstruct();
			// Run
			Set<ForeignKeyMDO> returned = unitUnderTest.read(SCHEME_NAME, TABLE_NAME, connection);
			// Check
			assertEquals(expected, returned);
		}
	}
}
