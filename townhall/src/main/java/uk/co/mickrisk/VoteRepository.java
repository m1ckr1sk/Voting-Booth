package uk.co.mickrisk;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface VoteRepository extends Repository<Vote, Long> {

	public void save(Vote vote);

	public List<Vote> findAll();

	@Query("SELECT count(*) from Vote")
	public int countVotes();

	/*
	 * @Query(
	 * "SELECT COUNT(Id) votes, candidateId FROM Vote GROUP BY candidateId")
	 * public List<VoteResult> getResults();
	 */

	@Query(value = "select count(v), candidateId from Vote v group by candidateId", countQuery = "select count(1) from (select count(1) from UserEntity v group by candidateId) z")
	public List<Object[]> getResults();

	public Long deleteAll();

}
