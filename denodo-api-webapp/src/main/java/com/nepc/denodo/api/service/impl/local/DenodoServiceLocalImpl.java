package com.nepc.denodo.api.service.impl.local;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nepc.denodo.api.dto.AsOfDateDto;
import com.nepc.denodo.api.dto.ClientPlanDto;
import com.nepc.denodo.api.service.DenodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Profile("local")
@Service
@Transactional(propagation = Propagation.REQUIRED)
@Slf4j
public class DenodoServiceLocalImpl implements DenodoService
{
	private ObjectMapper objectMapper;

	@Autowired
	public DenodoServiceLocalImpl(ObjectMapper objectMapper)
	{
		this.objectMapper = objectMapper;
	}

	@Override
	public List<ClientPlanDto> getAllClientPlans()
	{

		throw new RuntimeException("lol");

//		try
//		{
//			return objectMapper.readValue(readFile("mock/clientPlans.json"), new TypeReference<List<ClientPlanDto>>()
//			{
//			});
//
//		}
//		catch (Exception e)
//		{
//			return null;
//		}
	}

	@Override
	public List<AsOfDateDto> getAllAsOfDates()
	{
		try
		{
			return objectMapper.readValue(readFile("mock/asOfDates.json"), new TypeReference<List<AsOfDateDto>>()
			{
			});
		}
		catch (Exception e)
		{
			return null;
		}
	}

	private String readFile(String fileName) throws Exception
	{
		Path path = Paths.get(getClass().getClassLoader().getResource(fileName).toURI());
		StringBuilder data = new StringBuilder();
		Stream<String> lines = Files.lines(path);
		lines.forEach(line -> data.append(line).append("\n"));
		lines.close();
		return data.toString();
	}

}
