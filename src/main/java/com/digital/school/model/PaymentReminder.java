package com.digital.school.model;

import com.digital.school.config.school.SchoolAware;
import jakarta.persistence.*;
import org.hibernate.annotations.Filter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_reminders")
@Filter(name = "schoolFilter", condition = "school_id = :schoolId")
public class PaymentReminder extends AuditableEntity implements SchoolAware {

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id", nullable = false)
	private School school;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;
    
    private String type; // EMAIL, SMS
    private String status; // SENT, FAILED
    private LocalDateTime sentAt;
    private String content;
    
    
    public PaymentReminder() {
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getSentAt() {
		return sentAt;
	}
	public void setSentAt(LocalDateTime sentAt) {
		this.sentAt = sentAt;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
    
    // Getters and setters
    
    
    
}
