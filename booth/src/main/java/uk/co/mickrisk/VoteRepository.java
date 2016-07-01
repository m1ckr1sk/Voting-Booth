package uk.co.mickrisk;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface VoteRepository extends Repository<Vote, Long>{
	
	public void save(Vote vote);
	public List<Vote> findAll();
	public Long deleteAll();
	
	@Query("SELECT count(*) from Vote")
	public int countVotes();

}
