package uk.co.mickrisk;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.research.ws.wadl.Request;

import ch.qos.logback.access.pattern.RequestMethodConverter;

@RestController
public class BoothController {

	protected Logger logger = Logger.getLogger(BoothController.class.getName());
	protected ObjectMapper mapper = new ObjectMapper();

	protected VoteRepository voteRepository;

	private CandidateValidator candidateValidator;
	private VoterValidator voterValidator;
	
	@Value("${booth.booth_identifier}")
	String boothIdentifier;

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

		voterValidator.registerVote(voter.getVoterId());
		
		Vote vote = new Vote(candidateId);
		voteRepository.save(vote);
		
		return "{\"message\":\"vote successfully cast\"}";
	}
	
	@RequestMapping(value = "/close", method = RequestMethod.GET)
	public String closeBooth() throws NumberFormatException, JsonProcessingException {
		VoteCollection voteCollection = new VoteCollection(new Long(boothIdentifier));
		voteCollection.setVotes(voteRepository.findAll());
		return mapper.writeValueAsString(voteCollection);
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.DELETE)
	@Transactional
	public String deleteVotes(){
		voteRepository.deleteAll();
		return "Deleted all votes";
	}


}
