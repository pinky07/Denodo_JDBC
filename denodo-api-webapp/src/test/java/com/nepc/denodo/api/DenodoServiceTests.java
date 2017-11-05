package com.nepc.denodo.api;

import com.nepc.denodo.api.dto.AsOfDateDto;
import com.nepc.denodo.api.dto.ClientPlanDto;
import com.nepc.denodo.api.entity.ClientPlan;
import com.nepc.denodo.api.exception.NepcDenodoException;
import com.nepc.denodo.api.service.DenodoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertEquals;

public class DenodoServiceTests
{
	ClientPlanDto clientPlanDto = new ClientPlanDto();
	AsOfDateDto asOfDateDto = new AsOfDateDto();
	ClientPlan clientPlan= new ClientPlan();
	@Autowired DenodoService denodoService;
	private JdbcTemplate jdbcTemplate;

	private ModelMapper modelMapper;
	//DenodoServiceJdbcTemplateImpl impl = new DenodoServiceJdbcTemplateImpl( jdbcTemplate,modelMapper);

	@Test
	public void testgetAllClientPlans()throws NepcDenodoException
	{
		clientPlanDto.setClientName("University of Central Florida Foundation, Inc");
		clientPlanDto.setClientSegment("Not For Profit / Charity");
		clientPlanDto.setIfCode("R5C2000");
		clientPlanDto.setPlanGuid("B072C77D-6E16-E411-A1B5-00505694607E");
		clientPlanDto.setPlanName("University of Central Florida Foundation, Inc - Non-Endowed Fund Pool");
		List<ClientPlanDto> actual =  new ArrayList<ClientPlanDto>();
		actual.add(clientPlanDto);
		/**
		 * calling test method,with dummy values
		 */

		List<ClientPlanDto>	expected = denodoService.getAllClientPlans();
		assertEquals(expected,actual);
	}
	@Test
	public void testgetAllAsOfDates()throws NepcDenodoException{
		LocalDate today = LocalDate.now();
		asOfDateDto.setLocalDate(today);
		List<AsOfDateDto>actual = new ArrayList<AsOfDateDto>();
		actual.add(asOfDateDto);
		/**
		 * calling test method,with dummy values
		 */
		List<AsOfDateDto> expected= denodoService.getAllAsOfDates();
		/*
		* varify that function excuting the code succesfully or not*/
		assertEquals(expected,actual);
	}
}
