package com.nepc.denodo.api.service.impl.dev;

import com.nepc.denodo.api.dto.AsOfDateDto;
import com.nepc.denodo.api.dto.ClientPlanDto;
import com.nepc.denodo.api.entity.AsOfDate;
import com.nepc.denodo.api.entity.ClientPlan;
import com.nepc.denodo.api.service.DenodoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Profile("dev")
@Service
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class DenodoServiceJdbcTemplateImpl implements DenodoService
{
	private JdbcTemplate jdbcTemplate;

	private ModelMapper modelMapper;

	@Autowired
	public DenodoServiceJdbcTemplateImpl(JdbcTemplate jdbcTemplate, ModelMapper modelMapper)
	{
		this.jdbcTemplate = jdbcTemplate;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ClientPlanDto> getAllClientPlans()
	{
		log.info("Querying dv_clientplanlist");
		return jdbcTemplate.query("SELECT * FROM dv_clientplanlist ", (rs, rowNum) -> new ClientPlan(rs, rowNum))
				.stream().map(clientPlan -> modelMapper.map(clientPlan, ClientPlanDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<AsOfDateDto> getAllAsOfDates()
	{
		log.info("Querying bv_if_asofdates_stg");
		return jdbcTemplate.query("SELECT * FROM dv_if_asofdates_stg", (rs, rowNum) -> new AsOfDate(rs, rowNum))
				.stream().map(asOfDate -> modelMapper.map(asOfDate, AsOfDateDto.class)).collect(Collectors.toList());
	}
}
