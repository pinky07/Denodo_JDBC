package com.nepc.denodo.api.endpoint;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Redirects /swagger to /swagger-ui.html.
 *
 * @author Ruben Jimenez
 */
@Controller
public class SwaggerEndpoint
{

	/**
	 * Redirects /swagger to /swagger-ui.html.
	 *
	 * @return Swagger html page
	 */
	@GetMapping(value = "/swagger")
	public String getSwagger()
	{
		return "redirect:swagger-ui.html";
	}
}
