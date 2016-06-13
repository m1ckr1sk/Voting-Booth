package uk.co.mickrisk;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

@Component
public class VoteCloser {
	
	protected Logger logger = Logger.getLogger(VoteCloser.class.getName());
	private RestOperations restTemplate;
	private DiscoveryClient discoveryClient;
	
	@Autowired
	public VoteCloser(RestOperations restTemplate,DiscoveryClient discoveryClient){
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
	}
	
	public List<String> getBoothUrls() throws InterruptedException
	{
		List<ServiceInstance> serviceInstances = null;
		List<String> boothUrls = new ArrayList<String>();
		long time = System.currentTimeMillis();
		long timeout = time + 60000;
		while(System.currentTimeMillis() < timeout)
		{
			serviceInstances = discoveryClient.getInstances("voting-booth");
			if(serviceInstances != null && serviceInstances.size() > 0)
			{
				for(ServiceInstance serviceInstance : serviceInstances){
					logger.info("voting-booth Url:" + serviceInstance.getUri());
					boothUrls.add(serviceInstance.getUri().toString());
				}
				
				return boothUrls;
			}
			Thread.sleep(1000);
		}
		return boothUrls;
	}
	
	public void closeVote() throws InterruptedException{
		List<String> boothUrls = getBoothUrls();
		for(String boothUrl : boothUrls){
			
			VoteCollection message = this.restTemplate.getForObject(boothUrl + "/close", VoteCollection.class);
			logger.info("Booth: " + message.getBoothId());
			
			List<Vote> votes = message.getVotes();
			for(Vote vote : votes){
				logger.info("VoteID: " + vote.getVoteId() + " for candidate:" + vote.getCandidateId());
			}
		}
	}
}
