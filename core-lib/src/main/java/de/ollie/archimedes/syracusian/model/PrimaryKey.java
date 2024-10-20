package de.ollie.archimedes.syracusian.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Data
@Generated
public class PrimaryKey {

	private UUID id;
	private String name;
	private List<Column> columns = new ArrayList<>();
}
