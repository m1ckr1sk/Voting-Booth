package uk.co.mickrisk;

import org.springframework.data.repository.Repository;

public interface BoothRepository extends Repository<Booth, Long>{
	
	public Booth findByBoothId(Long boothId);
	public void save(Booth booth);
	public void deleteByBoothId(Long boothId);
}
