package com.ezen.spg15.service;

import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;

import com.ezen.spg15.dao.ITransactionDao1;
import com.ezen.spg15.dao.ITransactionDao2;

@Service
public class BuyTicketService implements IBuyTicketService{

	@Autowired
	ITransactionDao1 tdao1;
	
	@Autowired 
	ITransactionDao2 tdao2;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	TransactionDefinition defintion;
	
	@Override
	public int buy(String id, int amount, String error) {
		
		// Transaction의 시작 = 끝은 리턴
		TransactionStatus status = transactionManager.getTransaction(defintion);
		
		try {
			tdao1.pay(id, amount); //결과의 내용을 보기 위해 (별 의미 없음)
			if(error.equals("1")) {int n=10/0;} //의도적 에러 발생
			tdao2.pay(id, amount);
			transactionManager.commit(status);
			System.out.println("Transaction Commit");
			return 1; //잘실행되면 1이 mycontroller로 리턴됨
		}catch(Exception e){  //에러가 나면 실행 취소 개념으로 접근
			transactionManager.rollback(status);
			System.out.println("Transaction RollBack");
			return 0;
		}
	}
	
	
}