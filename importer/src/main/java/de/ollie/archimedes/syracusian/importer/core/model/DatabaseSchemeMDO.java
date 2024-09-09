package de.ollie.archimedes.syracusian.importer.core.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@Data
@Generated
public class DatabaseSchemeMDO {

	private String name;
	private Set<TableMDO> tables;
}
