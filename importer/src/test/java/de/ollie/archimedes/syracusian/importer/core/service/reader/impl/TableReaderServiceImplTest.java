package de.ollie.archimedes.syracusian.importer.core.service.reader.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.model.TableMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseTypeService;
import de.ollie.archimedes.syracusian.importer.core.service.reader.accessor.TableAccessor;
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
class TableReaderServiceImplTest {

	private static final String SCHEME_NAME = "scheme-name";

	@Mock
	private TableAccessor accessor0;

	@Mock
	private TableAccessor accessor1;

	@Mock
	private DatabaseTypeService databaseTypeService;

	@Mock
	private List<TableAccessor> accessors;

	@Mock
	private Connection connection;

	@InjectMocks
	private TableReaderServiceImpl unitUnderTest;

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
	class TestsOfMethod_read_Connection {

		@Test
		void throwsAnException_passingANullValueAsConnection() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.read(SCHEME_NAME, null));
		}

		@Test
		void throwsAnException_whenAnAccessorIsDefinedForDatabaseTypeUNSPECIFIEDOnly() {
			// Prepare
			Set<TableMDO> expected = Set.of();
			when(accessor0.getDatabaseType()).thenReturn(DatabaseType.UNSPECIFIED);
			when(accessor0.getTables(SCHEME_NAME, connection)).thenReturn(expected);
			when(accessors.stream()).thenReturn(Stream.of(accessor0));
			when(databaseTypeService.getDatabaseType(connection)).thenReturn(DatabaseType.HSQL);
			unitUnderTest.postConstruct();
			// Run
			Set<TableMDO> returned = unitUnderTest.read(SCHEME_NAME, connection);
			// Check
			assertEquals(expected, returned);
		}

		@Test
		void throwsAnException_whenAnAccessorIsDefinedForSpecifiedDatabaseType() {
			// Prepare
			Set<TableMDO> expected = Set.of();
			when(accessor0.getDatabaseType()).thenReturn(DatabaseType.UNSPECIFIED);
			when(accessor1.getDatabaseType()).thenReturn(DatabaseType.HSQL);
			when(accessor1.getTables(SCHEME_NAME, connection)).thenReturn(expected);
			when(accessors.stream()).thenReturn(Stream.of(accessor0, accessor1));
			when(databaseTypeService.getDatabaseType(connection)).thenReturn(DatabaseType.HSQL);
			unitUnderTest.postConstruct();
			// Run
			Set<TableMDO> returned = unitUnderTest.read(SCHEME_NAME, connection);
			// Check
			assertEquals(expected, returned);
		}
	}
}
