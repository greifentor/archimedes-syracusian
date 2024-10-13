package de.ollie.archimedes.syracusian.shell.command;

import de.ollie.archimedes.syracusian.importer.core.model.DatabaseSchemeMDO;
import de.ollie.archimedes.syracusian.importer.core.service.DatabaseSchemeImporterService;
import de.ollie.archimedes.syracusian.importer.core.service.JDBCConnectionDataFactory;
import de.ollie.archimedes.syracusian.model.JDBCConnectionData;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class ImporterCommands {

	private final DatabaseSchemeImporterService databaseSchemeImporterService;
	private final JDBCConnectionDataFactory jdbcConnectionDataFactory;

	@ShellMethod(value = "Imports a database scheme.", key = { "import", "imp" })
	public String importDatabaseScheme(
		@ShellOption(value = { "schemeName", "sn" }, help = "The name of the scheme to import.") String schemeName,
		@ShellOption(value = { "driverClassName", "dcn" }, help = "The driver class name.") String driverClassName,
		@ShellOption(value = { "databaseUrl", "url" }, help = "The URL to access the database.") String databaseUrl,
		@ShellOption(
			value = { "databaseUserName", "user" },
			help = "The user name to access the database."
		) String databaseUserName,
		@ShellOption(value = { "databasePassword", "pw" }, help = "The password for the user.") String databaseUserPassword
	) {
		JDBCConnectionData connectionData = jdbcConnectionDataFactory.create(
			driverClassName,
			databaseUrl,
			databaseUserName,
			databaseUserPassword
		);
		DatabaseSchemeMDO databaseMDO = databaseSchemeImporterService.readDatabaseScheme(schemeName, connectionData);
		return databaseMDO.toString();
	} // TODO OLI: Create an integration test.
}
