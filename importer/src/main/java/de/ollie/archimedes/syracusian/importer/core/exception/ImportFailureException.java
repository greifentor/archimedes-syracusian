package de.ollie.archimedes.syracusian.importer.core.exception;

import static de.ollie.archimedes.syracusian.util.Check.ensure;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
public class ImportFailureException extends RuntimeException {

	public enum ReasonType {
		CONNECTION_READ_ERROR,
		DATABASE_TYPE_READ_ERROR,
		SCHEME_NAME_READ_ERROR,
	}

	@AllArgsConstructor
	@Getter
	@EqualsAndHashCode
	public static class MessageParameter {

		private String name;
		private Object value;
	}

	private ReasonType reasonType;
	private Map<String, Object> parameters = new HashMap<>();

	public ImportFailureException(String message, ReasonType reasonType, Throwable e, MessageParameter... parameters) {
		super(message, e);
		ensure(reasonType != null, "reasonType cannot be null!");
		this.reasonType = reasonType;
		for (int i = 0; i < parameters.length; i++) {
			this.parameters.put(parameters[i].getName(), parameters[i].getValue());
		}
	}

	public String getMessageFormatted() {
		String s = getMessage().replace("{REASON_TYPE}", getReasonType().name());
		for (String key : parameters.keySet()) {
			s = getMessage().replace("{key}", "" + parameters.get(key));
		}
		return s;
	}
}
