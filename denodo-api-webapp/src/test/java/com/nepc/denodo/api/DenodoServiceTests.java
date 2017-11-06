package com.nepc.denodo.api;

import com.nepc.denodo.api.dto.AsOfDateDto;
import com.nepc.denodo.api.dto.ClientPlanDto;
import com.nepc.denodo.api.entity.AsOfDate;
import com.nepc.denodo.api.entity.ClientPlan;
import com.nepc.denodo.api.exception.NepcDenodoException;
import com.nepc.denodo.api.service.DenodoService;
import com.nepc.denodo.api.service.impl.dev.DenodoServiceJdbcTemplateImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import static org.springframework.test.web.client.ExpectedCount.times;
import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class DenodoServiceTests
{
	ClientPlanDto clientPlanDto = new ClientPlanDto();
	AsOfDateDto asOfDateDto = new AsOfDateDto();
	ClientPlan clientPlan= new ClientPlan();
	AsOfDate asOfDate = new AsOfDate();
	@Mock
	@Autowired DenodoService denodoService;
	@Mock
	private JdbcTemplate jdbcTemplate;
	@Mock
	private ModelMapper modelMapper;
	//DenodoServiceJdbcTemplateImpl impl = new DenodoServiceJdbcTemplateImpl( jdbcTemplate,modelMapper);

	@BeforeMethod
	public void setUp() throws Exception
	{
		MockitoAnnotations.initMocks(this);
		this.denodoService = new DenodoServiceJdbcTemplateImpl(jdbcTemplate, modelMapper);
	}

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
		//given
		LocalDate sampleDate = LocalDate.of(2015, 12, 31);
		asOfDateDto.setLocalDate(sampleDate);
		List<AsOfDateDto>actual = new ArrayList<AsOfDateDto>();
		actual.add(asOfDateDto);
		/**
		 * calling test method,with dummy values
		 */
		//when
		List<AsOfDateDto> expected= denodoService.getAllAsOfDates();
		/*
		* varify that function excuting the code succesfully or not*/
		// Then

		verify(jdbcTemplate.query("SELECT * FROM dv_if_asofdates_stg", (rs, rowNum) -> new AsOfDate(rs, rowNum))
						.stream().map(asOfDate -> modelMapper.map(asOfDate, AsOfDateDto.class)).collect(Collectors.toList()).size()>1);
		verify(modelMapper).map(asOfDate, asOfDateDto);
		assertEquals(expected.size(),actual.size());
//		verify(modelMapper, times(1)).map(asOfDate, asOfDateDto.class);
	}
}
