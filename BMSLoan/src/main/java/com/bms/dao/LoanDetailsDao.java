package com.bms.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bms.model.LoanDetails;

@Repository
public interface LoanDetailsDao extends JpaRepository<LoanDetails, String> {
	public List<LoanDetails> findAllByUsername(String username);
}
