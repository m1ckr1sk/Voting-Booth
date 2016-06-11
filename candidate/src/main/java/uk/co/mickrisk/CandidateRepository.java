package uk.co.mickrisk;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface CandidateRepository extends Repository<Candidate, Long>{
	
	public Candidate findByCandidateId(Long candidateId);
	public Candidate findByCandidateName(String candidateName);
	public List<Candidate> findAll();
	public void save(Candidate candidate);
		
	@Query("SELECT count(*) from Candidate")
	public int countCandidates();

}
