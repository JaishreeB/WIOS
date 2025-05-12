package com.cts.wios.dto;

import java.util.List;

import com.cts.wios.model.TransactionLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionResponseDTO {

	private UserRole user;
	private List<TransactionLog> transactionLog;

}
