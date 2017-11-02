package com.nepc.denodo.api.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Beans required by the application.
 *
 * @author Minor Madrigal
 * @author Rubén Jiménez
 */
@Configuration
@EnableAutoConfiguration
public class BeanConfiguration
{
	/**
	 * Model Mapper with configurations custom to this application
	 *
	 * @return Model Mapper Bean
	 */
	@Bean
	public ModelMapper modelMapper()
	{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

	/**
	 * Configuration to have Jackson write Java 8 dates in ISO format, for example: 2017-01-01T00:00:00-06:00.
	 *
	 * @param builder Jackson Builder
	 * @return Object Mapper Bean
	 */
	@Bean
	@Primary
	public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder)
	{
		ObjectMapper objectMapper = builder.createXmlMapper(false).build();
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		return objectMapper;
	}
}
