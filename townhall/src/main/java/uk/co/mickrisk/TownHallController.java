package uk.co.mickrisk;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class TownHallController {

	protected Logger logger = Logger.getLogger(TownHallController.class.getName());
	protected ObjectMapper mapper = new ObjectMapper();
	protected BoothRepository boothRepository;
	protected VoteCloser voteCloser;

	@Autowired
	public TownHallController(BoothRepository boothRepository, VoteCloser voteCloser) {
		this.boothRepository = boothRepository;
		this.voteCloser = voteCloser;
	}

	@RequestMapping(value = "/register/{boothId}", method = RequestMethod.POST)
	public String registerBooth(@PathVariable("boothId") long boothId) {

		Booth booth = new Booth();
		boothRepository.save(booth);
		
		return "{\"message\":\"booth "+booth.getBoothId()+"successfully registered\"}";
	}
	
	@RequestMapping(value = "/unregister/{boothId}", method = RequestMethod.POST)
	@Transactional
	public String unRegisterBooth(@PathVariable("boothId") long boothId) {

		boothRepository.deleteByBoothId(boothId);
		return "{\"message\":\"booth "+boothId+"successfully unregistered\"}";
	}
	
	@RequestMapping(value = "/booth/{boothId}", method = RequestMethod.GET)
	public String getBooth(@PathVariable("boothId") long boothId) throws JsonProcessingException {

		Booth booth = boothRepository.findByBoothId(boothId);
		if(booth != null)
		{
			return mapper.writeValueAsString(booth);
		}
		return "{\"message\":\"booth "+boothId+" not found\"}";
	}
	
	@RequestMapping(value = "/close", method = RequestMethod.POST)
	public String closeVote() throws InterruptedException {

		voteCloser.closeVote();
		return "{\"message\":\"closing vote\"}";
	}

}
