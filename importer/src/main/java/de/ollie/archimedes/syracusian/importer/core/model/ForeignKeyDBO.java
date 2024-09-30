package de.ollie.archimedes.syracusian.importer.core.model;

import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Generated
public class ForeignKeyDBO {

	private String name;
	private String columnName;
	private String referencedColumnName;
	private String referencedTableName;
}
