Feature: Voting booth
As a Voter	
I want to be able to vote for a candidate	
So that I can help choose who will be president

Scenario: Can cast a vote
Given I have voting booth service
And I have candidate service
And I have voter service
When I add candidate "Mr. Steve Pitcher"
And I add voter "Mrs. Linda Evans"
And I cast a vote from "Mrs. Linda Evans" for "Mr. Steve Pitcher"
Then the voter "Mrs. Linda Evans" must have registered a vote