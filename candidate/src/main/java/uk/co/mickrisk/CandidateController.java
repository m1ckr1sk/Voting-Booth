package uk.co.mickrisk;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CandidateController {

	protected Logger logger = Logger.getLogger(CandidateController.class.getName());
	protected CandidateRepository candidateRepository;

	@Autowired
	public CandidateController(CandidateRepository candidateRepository) {
		

		logger.info("CandidateRepository says system has " + candidateRepository.countCandidates() + " candidates");
	}

	@RequestMapping(value = "/candidates",method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Candidate> getCandidates() {
		return candidateRepository.findAll();
	}

	@RequestMapping(value = "/candidate/{candidateName}", method = RequestMethod.POST)
	@ResponseBody
	public String castVote(@PathVariable("candidateName") String candidateName) {
		Candidate candidate = new Candidate(candidateName);
		candidateRepository.save(candidate);
		return "Added:" + candidateName;
	}

}
