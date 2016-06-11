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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BoothController {

	protected Logger logger = Logger.getLogger(BoothController.class.getName());

	protected VoteRepository voteRepository;

	private CandidateValidator candidateValidator;
	private VoterValidator voterValidator;

	@Autowired
	public BoothController(VoteRepository voteRepository, 
			CandidateValidator candidateValidator,
			VoterValidator voterValidator) {
		this.voteRepository = voteRepository;
		this.candidateValidator = candidateValidator;
		this.voterValidator = voterValidator;
	}

	@RequestMapping(value = "/vote/{candidateId}", method = RequestMethod.POST)
	public String castVote(@PathVariable("candidateId") long candidateId, @RequestBody Voter voter) {

		Boolean validCandidate = candidateValidator.validateCandidate(candidateId);
		if (!validCandidate) {
			return "{\"message\":\"failed to validate candidate\"}";
		}
		
		Boolean validVoter = voterValidator.validateVoter(voter.getVoterId());
		if (!validVoter) {
			return "{\"message\":\"failed to validate voter\"}";
		}

		Vote vote = new Vote(candidateId);
		voteRepository.save(vote);
		return "{\"message\":\"vote successfully cast\"}";

	}

}
