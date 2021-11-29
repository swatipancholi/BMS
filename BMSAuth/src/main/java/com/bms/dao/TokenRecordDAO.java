package com.bms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bms.model.TokenRecord;

@Repository
public interface TokenRecordDAO extends JpaRepository<TokenRecord, String> {

}
	