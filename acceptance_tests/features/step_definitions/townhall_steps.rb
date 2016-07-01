Given(/^I have townhall service$/) do
  puts "Contacting discovery:#{DISCOVERY_URL}/VOTING-TOWN-HALL"
  uri = URI("#{DISCOVERY_URL}/eureka/apps/VOTING-TOWN-HALL")
  res = Net::HTTP.get(uri)
  doc = REXML::Document.new res
  root = doc.root
  #puts docs
  @townhall_host =  doc.elements["//instance/hostName"].text
  @townhall_port = doc.elements["//instance/port"].text

  clear_all_townhall_votes
end

Then(/^I close the vote$/) do
  puts "Closing vote"
  uri = URI("http://#{@townhall_host}:#{@townhall_port}/townhallservice/close")
  http = Net::HTTP.new(uri.host, uri.port)

  #close the vote
  request = Net::HTTP::Put.new(uri)
  res = http.request(request)
  expect(res.code).to eq('200')
end

Then(/^the results must be presented correctly$/) do |expected_results_table|
  expected_results_data = expected_results_table.hashes
  puts "Getting results"
  uri = URI("http://#{@townhall_host}:#{@townhall_port}/townhallservice/result")
  http = Net::HTTP.new(uri.host, uri.port)

  #get the results
  request = Net::HTTP::Get.new(uri)
  res = http.request(request)
  expect(res.code).to eq('200')

  results = get_results(res)
  puts "RESULT:#{results}"
  puts "EXPECTED:#{expected_results_data}"
  
  expect(results).to eq(expected_results_data)

end

def get_results(res)
  votes = JSON.parse res.body
  candidates = get_all_candidates
  results = []
  votes.each do |vote|
    candidates.each do |candidate|
      if(candidate['candidateId'] == vote[1])
        results << {'candidate' => candidate['candidateName'], 'votes' => vote[0].to_s}
      end
    end
  end
  return results
end

def clear_all_townhall_votes
  uri = URI("http://#{@townhall_host}:#{@townhall_port}/townhallservice/reset")
  http = Net::HTTP.new(uri.host, uri.port)

  request = Net::HTTP::Delete.new(uri)
  res = http.request(request)
end

