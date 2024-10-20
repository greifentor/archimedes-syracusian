package de.ollie.archimedes.syracusian.importer.core.service.mapper.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import de.ollie.archimedes.syracusian.importer.core.factory.UUIDFactory;
import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.model.Datascheme;
import java.util.UUID;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DataschemeMDOMapperImplTest {

	private static final String NAME = "name";
	private static final UUID UID = UUID.randomUUID();

	@Mock
	private DatabaseSchemeMDO mdo;

	@Mock
	private UUIDFactory uuidFactory;

	@InjectMocks
	private DataschemeMDOMapperImpl unitUnderTest;

	@Nested
	class TestsOfMethod_toModel_DataschemeMDO {

		@Test
		void throwsAnException_passingANullValue() {
			assertThrows(IllegalArgumentException.class, () -> unitUnderTest.toModel(null));
		}

		@Test
		void setsIdCorrectly_passingADataschemeMDO() {
			// Prepare
			when(uuidFactory.create()).thenReturn(UID);
			// Run
			Datascheme returned = unitUnderTest.toModel(mdo);
			// Check
			assertEquals(UID, returned.getId());
		}

		@Test
		void setsNameCorrectly_passingADataschemeMDOWithANameSet() {
			// Prepare
			when(mdo.getName()).thenReturn(NAME);
			// Run
			Datascheme returned = unitUnderTest.toModel(mdo);
			// Check
			assertEquals(NAME, returned.getName());
		}
	}
}
