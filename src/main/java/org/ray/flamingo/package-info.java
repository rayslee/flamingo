@org.hibernate.annotations.GenericGenerator(
	name = "ID_GENERATOR",
	strategy = "enhanced-sequence",
	parameters = {
		@org.hibernate.annotations.Parameter(
			name = "sequence_name",
			value = "ID_SEQUENCE"),
		@org.hibernate.annotations.Parameter(
			name = "initial_value",
			value = "8080")
	})

/**
 * Customized id generator for all entities.
 *
 * @author Ray LEE
 * @since 1.0
 */
package org.ray.flamingo;
