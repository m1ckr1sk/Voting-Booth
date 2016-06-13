package uk.co.mickrisk;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Booth {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private	long boothId;
	private Boolean isOpen;
	
	public Booth(){
		isOpen = true;
	}

	public long getBoothId() {
		return boothId;
	}

	public void setBoothId(long boothId) {
		this.boothId = boothId;
	}

	public Boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(Boolean isOpen) {
		this.isOpen = isOpen;
	}

}
