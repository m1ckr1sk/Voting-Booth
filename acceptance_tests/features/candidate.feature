Feature: Candidate registration

Scenario: Can add a candidate
Given I have candidate service
When I add candidate "Mr. Steve Pitcher"
Then candidate "Mr. Steve Pitcher" must be eligible for the vote

Scenario: Can add multiple candidates
Given I have candidate service
When I add "100" candidate names
Then all candidates must be eligible for the vote