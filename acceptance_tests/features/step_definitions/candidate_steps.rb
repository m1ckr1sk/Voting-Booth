require 'net/http'
require "rexml/document"
require 'json'
require 'faker'

Given(/^I have candidate service$/) do
  puts "Contacting discovery:#{DISCOVERY_URL}/VOTING-CANDIDATE"
  uri = URI("#{DISCOVERY_URL}/eureka/apps/VOTING-CANDIDATE")
  res = Net::HTTP.get(uri)
  doc = REXML::Document.new res
  root = doc.root
  #puts docs
  @candidate_host =  doc.elements["//instance/hostName"].text
  @candidate_port = doc.elements["//instance/port"].text

  clear_all_candidates()
end

When(/^I add candidate "([^"]*)"$/) do |candidate_name|
  add_candidate(candidate_name)
end

Then(/^candidate "([^"]*)" must be eligible for the vote$/) do |expected_candidate|
  check_candidate(expected_candidate)
end

When(/^I add "([^"]*)" candidate names$/) do |number_of_names_to_add|
  @names = []
  for name_index in 0..number_of_names_to_add.to_i
    @names << Faker::Name.name
  end

  @names.each do |name|
    add_candidate(URI::encode(name, /\W/))
  end
end

Then(/^all candidates must be eligible for the vote$/) do
  @names.each do |name|
    check_candidate(name)
  end
end

def add_candidate(candidate_name)
  puts "Addding #{candidate_name}"
  uri = URI("http://#{@candidate_host}:#{@candidate_port}/candidateservice/candidate/#{URI::encode(candidate_name, /\W/)}")
  http = Net::HTTP.new(uri.host, uri.port)

  #Add the candidate
  request = Net::HTTP::Post.new(uri)
  res = http.request(request)
  expect(res.code).to eq('200')
end

def get_candidate(candidate_name)
  uri = URI("http://#{@candidate_host}:#{@candidate_port}/candidateservice/candidate/#{URI::encode(candidate_name, /\W/)}")
  res = Net::HTTP.get(uri)
  candidate = JSON.parse res
  return candidate
end

def check_candidate(expected_candidate)
  uri = URI("http://#{@candidate_host}:#{@candidate_port}/candidateservice/candidates")
  res = Net::HTTP.get(uri)
  candidates = JSON.parse res
  found_candidate = false
  puts "LOOKING FOR:#{expected_candidate}"
  candidates.each  do |candidate |
    puts "MAYBE:#{candidate["candidateName"]}"
    if expected_candidate == candidate["candidateName"]
      found_candidate = true
    end
  end

  expect(found_candidate).to be_truthy
end

def clear_all_candidates
  uri = URI("http://#{@candidate_host}:#{@candidate_port}/candidateservice/candidates")
  http = Net::HTTP.new(uri.host, uri.port)

  request = Net::HTTP::Delete.new(uri)
  res = http.request(request)
end
