package com.nepc.denodo.api.service;

import com.nepc.denodo.api.dto.AsOfDateDto;
import com.nepc.denodo.api.dto.ClientPlanDto;
import com.nepc.denodo.api.exception.NepcDenodoException;

import java.util.List;

public interface DenodoService
{
	List<ClientPlanDto> getAllClientPlans() throws NepcDenodoException;

	List<AsOfDateDto> getAllAsOfDates() throws NepcDenodoException;
}
