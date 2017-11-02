package com.nepc.denodo.api.entity;

import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ClientPlan
{
	@Setter
	@Getter
	private String clientSegment;

	@Setter
	@Getter
	private String clientName;

	@Setter
	@Getter
	private String planName;

	@Setter
	@Getter
	private String planGuid;

	@Setter
	@Getter
	private String ifCode;

	/**
	 * Creates a new object from a SQL Result Set.
	 *
	 * @param resultSet Result Set
	 * @param rowNumber Row Number
	 * @throws SQLException SQL Exception
	 */
	public ClientPlan(ResultSet resultSet, int rowNumber) throws SQLException
	{
		this.clientSegment = resultSet.getString("client_segment");
		this.clientName = resultSet.getString("client_name");
		this.planName = resultSet.getString("plan_name");
		this.planGuid = resultSet.getString("plan_guid");
		this.ifCode = resultSet.getString("if_code");
	}
}
