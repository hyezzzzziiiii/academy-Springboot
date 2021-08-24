package com.ezen.spg15;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.spg15.dao.ITransactionDao3;
import com.ezen.spg15.service.IBuyTicketService;

@Controller
public class MyController {
	
	@Autowired
	IBuyTicketService bs;

	@Autowired
	ITransactionDao3 tdao3;
	
	@Autowired
	TransactionTemplate transactionTemlate;
	
	@RequestMapping("/")
	public String root() throws Exception{
		
		return "buy_ticket";
	}
	
	@RequestMapping("/buy_ticket_card")
	public String buy_ticket_card(@RequestParam("id") String id,
			@RequestParam("amount") String amount, @RequestParam("error") String error,
			Model model) {
		model.addAttribute("id", id);
		model.addAttribute("amount",amount);
		
		try {
		transactionTemlate.execute(new TransactionCallbackWithoutResult() {	
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				int nResult = bs.buy(id, Integer.parseInt(amount), error);
				
				if(error.equals("2")) {int n = 10/0;}
				tdao3.pay(id, Integer.parseInt(amount));
				System.out.println("Transaction #2 Commit");
			}
		});
		 	return "buy_ticket_end"; 
		}catch(Exception r){
			System.out.println("Transaction #2 Rollback");
			return "buy_ticket_error";
		}
	}
	// jsp에서 얻어온 값을 service로 가져감
}
