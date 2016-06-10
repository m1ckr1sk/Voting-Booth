package uk.co.mickrisk;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vote {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long voteId;
	
	long candidateId;
	
	public Vote(long candidateId) {
		this.candidateId = candidateId;
	}

	
}
