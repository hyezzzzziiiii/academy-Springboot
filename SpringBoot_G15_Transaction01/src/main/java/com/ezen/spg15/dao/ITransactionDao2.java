package com.ezen.spg15.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ITransactionDao2 {
	public void pay(String id, int amount);
}
