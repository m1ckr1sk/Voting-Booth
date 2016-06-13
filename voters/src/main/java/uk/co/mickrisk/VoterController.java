package uk.co.mickrisk;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VoterController {

	protected Logger logger = Logger.getLogger(VoterController.class.getName());
	protected VoterRepository voterRepository;

	@Autowired
	public VoterController(VoterRepository voterRepository) {
		this.voterRepository = voterRepository;
		logger.info("VoterRepository says system has " + voterRepository.countVoters() + " voters");
	}

	@RequestMapping(value = "/voters", method = RequestMethod.GET)
	public List<Voter> getVoters() {
		List<Voter> voters = voterRepository.findAll();
		return voters;
	}

	@RequestMapping(value = "/voter/{voterName}", method = RequestMethod.POST)
	public String castVote(@PathVariable("voterName") String voterName) {
		Voter voter = new Voter(voterName);
		voterRepository.save(voter);
		return "Added:" + voterName;
	}

	@RequestMapping(value = "/voter/cast/{voterId}", method = RequestMethod.POST)
	public String castVote(@PathVariable("voterId") long voterId) {
		Voter voter = voterRepository.findByVoterId(voterId);
		if (voter == null) {
			return "{\"message\":\"Failed to find voter\"}";
		}

		if (voter.getHasVoted()) {
			return "{\"message\":\"voter has already regiestered a vote\"}";
		}

		voter.setHasVoted(true);
		voterRepository.save(voter);
		return "{\"message\":\"voter has regiestered a vote\"}";
	}

}
