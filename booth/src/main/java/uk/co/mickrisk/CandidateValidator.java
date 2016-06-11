package uk.co.mickrisk;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Component
public class CandidateValidator {
	
	protected Logger logger = Logger.getLogger(CandidateValidator.class.getName());
	private RestOperations restTemplate;
	private DiscoveryClient discoveryClient;
	
	@Autowired
	public CandidateValidator(RestOperations restTemplate,DiscoveryClient discoveryClient){
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
	}
	
	public String getCandidateServiceUrl() throws InterruptedException
	{
		List<ServiceInstance> serviceInstances = null;
		long time = System.currentTimeMillis();
		long timeout = time + 60000;
		while(System.currentTimeMillis() < timeout)
		{
			serviceInstances = discoveryClient.getInstances("voting-candidate");
			if(serviceInstances != null && serviceInstances.size() > 0)
			{
				logger.info("voting-candidate Url:" + serviceInstances.get(0).getUri());
				return serviceInstances.get(0).getUri().toString();
			}
			Thread.sleep(1000);
		}
		return "";
	}
	
	public Boolean validateCandidate(long candidateId) {
		
		String url = "";
		try {
			url = getCandidateServiceUrl();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		
		ResponseEntity<List<Candidate>> exchange =
                this.restTemplate.exchange(
                		url + "/candidates",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<Candidate>>() {
                        },
                        (Object) "mstine");

		Boolean foundCandidate = false;
        List<Candidate> candidates = exchange.getBody();
        for(Candidate candidate : candidates){
        	if(candidate.getCandidateId() == candidateId)
        	{
        		foundCandidate = true;
        	}
        }
    
        return foundCandidate;
	}

}
