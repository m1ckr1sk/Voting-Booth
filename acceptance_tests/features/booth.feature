Feature: Voting booth

Scenario: Can cast a vote
Given I have voting booth service
And I have candidate service
And I have voter service
When I add candidate "Mr. Steve Pitcher"
And I add voter "Mrs. Linda Evans"
And I cast a vote from "Mrs. Linda Evans" for "Mr. Steve Pitcher"
Then the voter "Mrs. Linda Evans" must have registered a vote