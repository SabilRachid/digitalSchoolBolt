package com.digital.school.model;

import com.digital.school.config.school.SchoolAware;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class Payment extends AuditableEntity implements SchoolAware {

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id", nullable = false)
	private School school;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal amount;
    
    private String paymentMethod; // CARD, CASH, BANK_TRANSFER
    private String transactionId;
    private String status; // PENDING, COMPLETED, FAILED
    private LocalDateTime paymentDate;
    
    @Column(columnDefinition = "TEXT")
    private String receiptPath;
    
    private String errorMessage;
    
    public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public School getSchool() { return school; }
	@Override
	public void setSchool(School school) { this.school = school; }

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getReceiptPath() {
		return receiptPath;
	}

	public void setReceiptPath(String receiptPath) {
		this.receiptPath = receiptPath;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
    
  
    
    
    
}

