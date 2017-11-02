package com.nepc.denodo.api.endpoint;

import com.nepc.denodo.api.dto.AsOfDateDto;
import com.nepc.denodo.api.dto.ClientPlanDto;
import com.nepc.denodo.api.exception.NepcDenodoException;
import com.nepc.denodo.api.service.DenodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Tree endpoints.
 *
 * @author Minor Madrigal
 * @author Rubén Jiménez
 */
@RequestMapping("/api/v1")
@RestController
@Slf4j
public class DenodoEndpoint
{

	private DenodoService denodoService;

	/**
	 * Creates a new Object.
	 *
	 * @param denodoService Denodo Service
	 */
	@Autowired
	public DenodoEndpoint(DenodoService denodoService)
	{
		this.denodoService = denodoService;
	}

	@GetMapping(value = "/clientPlans", produces = "application/json")
	public ResponseEntity<List<ClientPlanDto>> getClientPlans() throws NepcDenodoException
	{
		log.debug("GET /clientPlans");
		List<ClientPlanDto> allClientPlans = denodoService.getAllClientPlans();
		return new ResponseEntity<>(allClientPlans, HttpStatus.OK);
	}

	@GetMapping(value = "/asOfDates", produces = "application/json")
	public ResponseEntity<List<AsOfDateDto>> getAsOfDates() throws NepcDenodoException
	{
		log.debug("GET /asOfDates");
		List<AsOfDateDto> asOfDates = denodoService.getAllAsOfDates();
		return new ResponseEntity<>(asOfDates, HttpStatus.OK);
	}
}
