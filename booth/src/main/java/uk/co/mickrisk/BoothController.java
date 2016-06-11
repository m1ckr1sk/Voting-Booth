package uk.co.mickrisk;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BoothController {

	protected Logger logger = Logger.getLogger(BoothController.class.getName());

	protected VoteRepository voteRepository;

	private CandidateValidator candidateValidator;

	@Autowired
	public BoothController(VoteRepository voteRepository, CandidateValidator candidateValidator) {
		this.voteRepository = voteRepository;
		this.candidateValidator = candidateValidator;
	}

	@RequestMapping(value = "/vote/{candidateId}", method = RequestMethod.POST)
	public String castVote(@PathVariable("candidateId") long candidateId) {

		logger.info("Checking the candidate");
		Boolean validCandidate = candidateValidator.validateCandidate(candidateId);
		logger.info("Candidate came back");
		if (!validCandidate) {
			return "{\"message\":\"failed to find candidate\"}";
		}

		logger.info("Creating the vote");
		Vote vote = new Vote(candidateId);
		logger.info("Saving the vote");
		voteRepository.save(vote);
		logger.info("Returning response");
		return "{\"message\":\"vote successfully cast\"}";

	}

}
