package com.proxibanksi.dtos;

public class TransfertRequestDTO {
	
	private Long accountSourceId;
	private Long accountDestinationId;
	private double amount;
    private String description;
    
	public Long getAccountSourceId() {
		return accountSourceId;
	}
	public void setAccountSourceId(Long accountSourceId) {
		this.accountSourceId = accountSourceId;
	}
	public Long getAccountDestinationId() {
		return accountDestinationId;
	}
	public void setAccountDestinationId(Long accountDestinationId) {
		this.accountDestinationId = accountDestinationId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
    
	
    
    
}
