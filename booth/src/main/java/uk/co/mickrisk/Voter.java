package uk.co.mickrisk;

public class Voter {

	private Long voterId;
	private String voterName;
	private Boolean hasVoted;

	public Voter() {
	};

	public Voter(String voterName) {
		super();
		this.voterName = voterName;
	}

	public String getVoterName() {
		return voterName;
	}

	public void setVoterName(String voterName) {
		this.voterName = voterName;
	}

	public Long getVoterId() {
		return voterId;
	}

	public void setvoterId(Long voterId) {
		this.voterId = voterId;
	}

	public Boolean getHasVoted() {
		return hasVoted;
	}

	public void setHasVoted(Boolean hasVoted) {
		this.hasVoted = hasVoted;
	}
}
