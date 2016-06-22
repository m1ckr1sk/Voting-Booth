package uk.co.mickrisk;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
		this.candidateRepository = candidateRepository;
		logger.info("CandidateRepository says system has " + candidateRepository.countCandidates() + " candidates");
	}

	@RequestMapping(value = "/candidates",method=RequestMethod.GET)
	public List<Candidate> getCandidates() {
		List<Candidate> candidates = candidateRepository.findAll();
		return candidates;
	}

	@RequestMapping(value = "/candidate/{candidateName:.+}", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public String castVote(@PathVariable("candidateName") String candidateName) {
		if(candidateRepository.findByCandidateName(candidateName) == null){
		Candidate candidate = new Candidate(candidateName);
		candidateRepository.save(candidate);
		logger.info("Added:" + candidateName);
		return "Added:" + candidateName;
		}
		
		return "Can not add " + candidateName + ".  Candidate already exists";
	}
	
	@RequestMapping(value = "/candidates", method = RequestMethod.DELETE)
	@Transactional
	public String deleteAll() {
		candidateRepository.deleteAll();
		return "Deleted all candidates";
	}
	
	@RequestMapping(value = "/candidate/{candidateName}", method = RequestMethod.DELETE)
	@Transactional
	public String deleteCandidate(@PathVariable("candidateName") String candidateName) {
		candidateRepository.deleteByCandidateName(candidateName);
		return "Deleted:" + candidateName;
	}

}
