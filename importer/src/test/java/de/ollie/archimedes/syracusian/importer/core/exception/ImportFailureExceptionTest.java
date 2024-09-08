package de.ollie.archimedes.syracusian.importer.core.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.MessageParameter;
import de.ollie.archimedes.syracusian.importer.core.exception.ImportFailureException.ReasonType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ImportFailureExceptionTest {

	private static final String MESSAGE = "message";
	private static final String MESSAGE_WITH_REASON_TYPE = "message {REASON_TYPE}";
	private static final String PARAMETER_NAME = "parameter-name";
	private static final String PARAMETER_VALUE = "parameter-message";
	private static final ReasonType REASON_TYPE = ReasonType.CONNECTION_READ_ERROR;

	private ImportFailureException unitUnderTest;

	@Nested
	class TestsOfConstructor_String_ReasonType_Throwable_Parameters {

		@Test
		void throwsAnException_passingANullValueAsReasonType() {
			assertThrows(
				IllegalArgumentException.class,
				() -> new ImportFailureException(MESSAGE, null, new RuntimeException())
			);
		}
	}

	@Nested
	class TestsOfMethod_getMessageFormatted {

		@Test
		void returnsCorrectMessage_withNoPlaceHolders() {
			// Prepare
			unitUnderTest = new ImportFailureException(MESSAGE, REASON_TYPE, null);
			// Run & Check
			assertEquals(MESSAGE, unitUnderTest.getMessageFormatted());
		}

		@Test
		void returnsCorrectMessage_withReasonPlaceHolder() {
			// Prepare
			unitUnderTest = new ImportFailureException(MESSAGE_WITH_REASON_TYPE, REASON_TYPE, null);
			// Run & Check
			assertEquals(
				MESSAGE_WITH_REASON_TYPE.replace("{REASON_TYPE}", REASON_TYPE.name()),
				unitUnderTest.getMessageFormatted()
			);
		}

		@Test
		void returnsCorrectMessage_withParameterPlaceHolder() {
			// Prepare
			unitUnderTest =
				new ImportFailureException(
					MESSAGE_WITH_REASON_TYPE,
					REASON_TYPE,
					null,
					new MessageParameter(PARAMETER_NAME, PARAMETER_VALUE)
				);
			// Run & Check
			assertEquals(
				MESSAGE_WITH_REASON_TYPE.replace("{" + PARAMETER_NAME + "}", PARAMETER_VALUE),
				unitUnderTest.getMessageFormatted()
			);
		}
	}
}
