package com.ezen.spg15;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.spg15.service.IBuyTicketService;

@Controller
public class MyController {
	
	@Autowired
	IBuyTicketService bs;

	@RequestMapping("/")
	public String root() throws Exception{
		
		return "buy_ticket";
	}
	
	@RequestMapping("/buy_ticket_card")
	public String buy_ticket_card(@RequestParam("id") String id,
			@RequestParam("amount") String amount, @RequestParam("error") String error,
			Model model) {
		int nResult = bs.buy(id, Integer.parseInt(amount), error);
			model.addAttribute("id", id);
			model.addAttribute("amount",amount);
			System.out.println(nResult);
		if(nResult ==1) return "buy_ticket_end"; 
		else return "buy_ticket_error";
		
	}
	// jsp에서 얻어온 값을 service로 가져감
	
}
