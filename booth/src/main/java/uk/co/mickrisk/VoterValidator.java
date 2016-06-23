package uk.co.mickrisk;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

@Component
public class VoterValidator {

	protected Logger logger = Logger.getLogger(VoterValidator.class.getName());
	private RestOperations restTemplate;
	private DiscoveryClient discoveryClient;

	@Autowired
	public VoterValidator(RestOperations restTemplate, DiscoveryClient discoveryClient) {
		this.restTemplate = restTemplate;
		this.discoveryClient = discoveryClient;
	}

	public String getVoterServiceUrl() throws InterruptedException {
		List<ServiceInstance> serviceInstances = null;
		long time = System.currentTimeMillis();
		long timeout = time + 60000;
		while (System.currentTimeMillis() < timeout) {
			serviceInstances = discoveryClient.getInstances("voting-voter");
			if (serviceInstances != null && serviceInstances.size() > 0) {
				logger.info("voting-voter Url:" + serviceInstances.get(0).getUri());
				return serviceInstances.get(0).getUri().toString();
			}
			Thread.sleep(1000);
		}
		return "";
	}

	public Boolean validateVoter(long voterId) {

		String url = "";
		try {
			url = getVoterServiceUrl();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		ResponseEntity<List<Voter>> exchange = this.restTemplate.exchange(url + "/voterservice/voters", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Voter>>() {
				}, (Object) "mstine");

		Boolean foundVoter = false;
		List<Voter> voters = exchange.getBody();
		for (Voter voter : voters) {
			if (voter.getVoterId() == voterId && !voter.getHasVoted()) {
				foundVoter = true;
			}
		}

		return foundVoter;
	}

	public Boolean registerVote(long voterId) {

		String url = "";
		try {
			url = getVoterServiceUrl();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		HttpEntity<String> request = new HttpEntity<>(new String());
		String response = 
				  restTemplate.postForObject(url + "/voterservice/voter/cast/" + voterId, request, String.class);

		logger.info(response);
		
		return true;
	}

}
