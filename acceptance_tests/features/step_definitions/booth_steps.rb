require 'net/http'

Given(/^I have voting booth service$/) do
  puts "Contacting discovery:#{DISCOVERY_URL}/VOTING-BOOTH"
  uri = URI("#{DISCOVERY_URL}/eureka/apps/VOTING-BOOTH")
  res = Net::HTTP.get(uri)
  doc = REXML::Document.new res
  root = doc.root
  @booth_host =  doc.elements["//instance/hostName"].text
  @booth_port = doc.elements["//instance/port"].text

  clear_all_booth_votes
end

When(/^I cast a vote from "([^"]*)" for "([^"]*)"$/) do |voter_name, candidate_name|
  cast_vote(candidate_name, voter_name)
end

Then(/^the voter "([^"]*)" must have registered a vote$/) do |voter_name|
  voter = get_voter(voter_name)
  expect(voter["hasVoted"]).to be_truthy
  close_booth()
end

def cast_vote(candidate_name, voter_name)
  voter = get_voter(voter_name)
  candidate = get_candidate(candidate_name)

  puts "Casting vote for #{candidate_name} with id #{candidate["candidateId"]} from #{voter_name}"
  uri = URI("http://#{@booth_host}:#{@booth_port}/boothservice/vote/#{candidate["candidateId"]}")
  http = Net::HTTP.new(uri.host, uri.port)

  #Add the candidate
  request = Net::HTTP::Post.new(uri, initheader = {'Content-Type' =>'application/json'})
  request.body = voter.to_json
  res = http.request(request)
  expect(res.code).to eq('200')
end

def close_booth
  uri = URI("http://#{@booth_host}:#{@booth_port}/boothservice/close")
  res = Net::HTTP.get(uri)
  votes = JSON.parse res
  puts votes
  return votes
end

def clear_all_booth_votes
  uri = URI("http://#{@booth_host}:#{@booth_port}/boothservice/reset")
  http = Net::HTTP.new(uri.host, uri.port)

  request = Net::HTTP::Delete.new(uri)
  res = http.request(request)
end