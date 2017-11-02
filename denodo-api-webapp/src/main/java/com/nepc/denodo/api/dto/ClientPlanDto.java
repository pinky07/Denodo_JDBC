package com.nepc.denodo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class ClientPlanDto
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
}
