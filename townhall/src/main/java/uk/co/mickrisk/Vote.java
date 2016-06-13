package uk.co.mickrisk;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vote {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private
	long voteId;
	private long candidateId;
	
	public Vote(){
		
	}
	
	public Vote(long candidateId) {
		this.candidateId = candidateId;
	}

	public long getVoteId() {
		return voteId;
	}

	public void setVoteId(long voteId) {
		this.voteId = voteId;
	}

	public long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(long candidateId) {
		this.candidateId = candidateId;
	}

	
}
