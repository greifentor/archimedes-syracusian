package de.ollie.archimedes.syracusian.importer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@Data
@Generated
public class JDBCConnectionData {

	private String driverClassName;
	private String databaseUrl;
	private String databaseUserName;
	private String databaseUserPassword;
}
