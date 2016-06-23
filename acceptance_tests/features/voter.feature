Feature: Voter registration

Scenario: Can add a voter
Given I have voter service
When I add voter "Mr. Steve Pitcher"
Then voter "Mr. Steve Pitcher" must be eligible to vote

Scenario: Can add multiple voters
Given I have voter service
When I add "100" voter names
Then all voters must be eligible to vote