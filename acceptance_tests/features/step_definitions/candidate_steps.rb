require 'net/http'
require "rexml/document"
require 'json'
require 'faker'

Given(/^I have candidate service$/) do
  puts "Contacting discovery:#{DISCOVERY_URL}/VOTING-CANDIDATE"
  uri = URI("#{DISCOVERY_URL}/eureka/apps")
  res = Net::HTTP.get(uri)
  doc = REXML::Document.new res
  root = doc.root
  #puts docs
  @host =  doc.elements["//instance/hostName"].text
  @port = doc.elements["//instance/port"].text

  clear_all_candidates()
end

When(/^I add candidate "([^"]*)"$/) do |candidate_name|
  add_candidate(URI::encode(candidate_name, /\W/))
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
  uri = URI("http://#{@host}:#{@port}/candidate/#{candidate_name}")
  http = Net::HTTP.new(uri.host, uri.port)

  #Add the candidate
  request = Net::HTTP::Post.new(uri)
  res = http.request(request)
  expect(res.code).to eq('200')
end

def check_candidate(expected_candidate)
  uri = URI("http://#{@host}:#{@port}/candidates")
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
  uri = URI("http://#{@host}:#{@port}/candidates")
  http = Net::HTTP.new(uri.host, uri.port)

  request = Net::HTTP::Delete.new(uri)
  res = http.request(request)
end
