package uk.co.mickrisk;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface VoterRepository extends Repository<Voter, Long>{
	
	public Voter findByVoterId(Long voterId);
	public Voter findByVoterName(String voterName);
	public List<Voter> findAll();
	public void save(Voter voter);
		
	@Query("SELECT count(*) from Voter")
	public int countVoters();

}
