package uk.co.mickrisk;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CandidateValidator {
	
	protected Logger logger = Logger.getLogger(CandidateValidator.class.getName());
	RestTemplate restTemplate;
	
	@Autowired
	public CandidateValidator(RestTemplate restTemplate){
		this.restTemplate = restTemplate;
	}
	
	public void getCandidates() {
		ResponseEntity<List<Candidate>> exchange =
                this.restTemplate.exchange(
                        "http://voting-candidate/candidates",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Candidate>>() {
                        },
                        (Object) "mstine");

        List<Candidate> candidates = exchange.getBody();
        for(Candidate candidate : candidates){
        	logger.info(candidate.getCandidateName());
        }
	}

}
