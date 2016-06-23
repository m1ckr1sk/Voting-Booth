require 'net/http'
require "rexml/document"
require 'json'
require 'faker'

Given(/^I have voter service$/) do
  puts "Contacting discovery:#{DISCOVERY_URL}/VOTING-VOTER"
  uri = URI("#{DISCOVERY_URL}/eureka/apps/VOTING-VOTER")
  res = Net::HTTP.get(uri)
  doc = REXML::Document.new res
  root = doc.root
  #puts docs
  @voter_host =  doc.elements["//instance/hostName"].text
  @voter_port = doc.elements["//instance/port"].text

  clear_all_voters()
end

When(/^I add voter "([^"]*)"$/) do |voter_name|
  add_voter(voter_name)
end

Then(/^voter "([^"]*)" must be eligible to vote$/) do |expected_voter|
  check_voter(expected_voter)
end

When(/^I add "([^"]*)" voter names$/) do |number_of_names_to_add|
  @names = []
  for name_index in 0..number_of_names_to_add.to_i
    @names << Faker::Name.name
  end

  @names.each do |name|
    add_voter(name)
  end
end

Then(/^all voters must be eligible to vote$/) do
  @names.each do |name|
     check_voter(name)
   end
end

def add_voter(voter_name)
  puts "Addding #{voter_name}"
  uri = URI("http://#{@voter_host}:#{@voter_port}/voterservice/voter/#{URI::encode(voter_name, /\W/)}")
  http = Net::HTTP.new(uri.host, uri.port)

  #Add the candidate
  request = Net::HTTP::Post.new(uri)
  res = http.request(request)
  expect(res.code).to eq('200')
end

def check_voter(expected_voter)
  uri = URI("http://#{@voter_host}:#{@voter_port}/voterservice/voters")
  res = Net::HTTP.get(uri)
  voters = JSON.parse res
  found_voter = false
  puts "LOOKING FOR:#{expected_voter}"
  voters.each  do |voter|
    puts "MAYBE:#{voter}"
    if expected_voter == voter["voterName"] 
      expect(voter["hasVoted"]).to be_falsey
      found_voter = true
    end
  end

  expect(found_voter).to be_truthy
end

def get_voter(voter_name)
  uri = URI("http://#{@voter_host}:#{@voter_port}/voterservice/voter/#{URI::encode(voter_name, /\W/)}")
  res = Net::HTTP.get(uri)
  voter = JSON.parse res
  return voter
end

def clear_all_voters
  uri = URI("http://#{@voter_host}:#{@voter_port}/voterservice/voters")
  http = Net::HTTP.new(uri.host, uri.port)

  request = Net::HTTP::Delete.new(uri)
  res = http.request(request)
end