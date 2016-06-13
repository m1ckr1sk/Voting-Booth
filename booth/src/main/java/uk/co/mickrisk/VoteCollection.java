package uk.co.mickrisk;

import java.util.ArrayList;
import java.util.List;

public class VoteCollection {
	private Long boothId;
	private List<Vote> votes;

	public VoteCollection(Long boothId) {
		this.boothId = boothId;
		this.votes = new ArrayList<Vote>();
	}

	public Long getBoothId() {
		return boothId;
	}

	public void setBoothId(Long boothId) {
		this.boothId = boothId;
	}

	public void setVotes(List<Vote> allVotes) {
		this.votes = allVotes;
	}
	
	public List<Vote> getVotes(){
		return this.votes;
	}
}
