package uk.co.mickrisk;

public class VoteResult {
	private Long candidateId;
	private int votes;

	public VoteResult() {

	}

	public VoteResult(Long candidateId, int votes) {
		this.candidateId = candidateId;
		this.votes = votes;
	}

	public Long getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(Long candidateId) {
		this.candidateId = candidateId;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
}
