package com.ezen.spg15.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.ezen.spg15.dao.ITransactionDao1;
import com.ezen.spg15.dao.ITransactionDao2;

@Service
public class BuyTicketService implements IBuyTicketService{

	@Autowired
	ITransactionDao1 tdao1;
	
	@Autowired 
	ITransactionDao2 tdao2;
	
	@Autowired
	TransactionTemplate transactionTemplate;
	
	// @Transactional(propagation = Propagation.REQUIRED) 관련 작업 모두 취소
	@Transactional(propagation = Propagation.REQUIRES_NEW) //현재 작업은 독립실행
	
	@Override
	public int buy(String id, int amount, String error) {
		
		try {
			transactionTemplate.execute( 
					new TransactionCallbackWithoutResult(){
						@Override
						protected void doInTransactionWithoutResult(TransactionStatus status) {
							tdao1.pay(id, amount); //결과의 내용을 보기 위해 (별 의미 없음)
							if(error.equals("1")) {int n=10/0;} //의도적 에러 발생
							tdao2.pay(id, amount);
							System.out.println("Transaction #1 Commit");
						}
				});
			return 1; //잘실행되면 1이 mycontroller로 리턴됨
		}catch(Exception e){  //에러가 나면 실행 취소 개념으로 접근
			System.out.println("Transaction #1 RollBack");
			return 0;
		}
	}
	
	
}