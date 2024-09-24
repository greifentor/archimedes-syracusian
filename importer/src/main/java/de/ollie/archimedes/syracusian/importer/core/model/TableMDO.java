package de.ollie.archimedes.syracusian.importer.core.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@NoArgsConstructor
@Data
@Generated
public class TableMDO {

	private String name;
	private Set<ColumnMDO> columns = new HashSet<>();

	public List<String> getColumnNames() {
		return columns.stream().map(ColumnMDO::getName).sorted().toList();
	}
}
