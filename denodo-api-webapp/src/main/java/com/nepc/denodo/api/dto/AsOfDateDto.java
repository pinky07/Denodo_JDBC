package com.nepc.denodo.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
public class AsOfDateDto
{
	@Setter
	@Getter
	private LocalDate localDate;
}
