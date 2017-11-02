package com.nepc.denodo.api.entity;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AsOfDate
{
	@Setter
	@Getter
	private LocalDate localDate;

	/**
	 * Creates a new object from a SQL Result Set.
	 *
	 * @param resultSet Result Set
	 * @param rowNumber Row Number
	 * @throws SQLException SQL Exception
	 */
	public AsOfDate(ResultSet resultSet, int rowNumber) throws SQLException
	{
		this.localDate = LocalDate.parse(resultSet.getString("as_of_date"));
	}
}

