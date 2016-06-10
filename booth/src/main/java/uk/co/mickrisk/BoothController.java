package uk.co.mickrisk;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoothController {
	
	@RequestMapping("/vote/{candidate}")
	public void castVote(@PathVariable("candidateId") long candidateId)
	{
		Vote vote = new Vote(candidateId);
	}
	
}
