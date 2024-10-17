package de.ollie.archimedes.syracusian.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@AllArgsConstructor
@Data
@Generated
public class UniqueConstraint {

	private UUID id;
	private String name;
	private List<Column> columns = new ArrayList<>();
}
