package uk.co.mickrisk;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface VoteRepository extends Repository<Vote, Long>{
	
	public void save(Vote vote);
	
	@Query("SELECT count(*) from Vote")
	public int countVotes();

}
