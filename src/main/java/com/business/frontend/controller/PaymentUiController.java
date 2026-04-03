
package com.business.frontend.controller;

import com.business.frontend.util.ApiHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/ui/payments")
public class PaymentUiController {

	private final RestTemplate restTemplate = new RestTemplate();
	private static final String BASE_URL = "http://localhost:8085/api";
	private final ObjectMapper objectMapper = new ObjectMapper();

	@GetMapping("/revenue")
	public String getRevenue(Model model, HttpSession session) {
		try {
			HttpEntity<Void> entity = new HttpEntity<>(ApiHelper.bearerHeaders(session));
			ResponseEntity<String> resp = restTemplate.exchange(BASE_URL + "/payments/revenue", HttpMethod.GET, entity,
					String.class);

			String json = resp.getBody();

			// Parse as List of Maps (yearly breakdown)
			List<Map<String, Object>> revenueList = objectMapper.readValue(json,
					new TypeReference<List<Map<String, Object>>>() {
					});

			// Calculate total
			double total = revenueList.stream().mapToDouble(r -> {
				Object amt = r.get("totalAmount");
				return amt != null ? Double.parseDouble(amt.toString()) : 0.0;
			}).sum();

			model.addAttribute("revenueList", revenueList);
			model.addAttribute("totalRevenue", total);

		} catch (Exception e) {
			model.addAttribute("error", "Could not load revenue: " + e.getMessage());
		}
		return "revenue";
	}
}
