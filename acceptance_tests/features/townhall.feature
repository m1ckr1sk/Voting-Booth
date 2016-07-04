Feature: Count votes for an election
As an Election official	
I want votes for each candidate to be counted	
So that I know who has won the election

Scenario: Can run an election with one voter one candidate
Given I have voting booth service
And I have candidate service
And I have voter service
And I have townhall service
When I add candidate "Mr. Steve Pitcher"
And I add voter "Mrs. Linda Evans"
And I cast a vote from "Mrs. Linda Evans" for "Mr. Steve Pitcher"
Then the voter "Mrs. Linda Evans" must have registered a vote
And I close the vote
Then the results must be presented correctly
| candidate         | votes |
| Mr. Steve Pitcher | 1     |

Scenario: Can run an election with ten voter one candidate
Given I have voting booth service
And I have candidate service
And I have voter service
And I have townhall service
When I add candidate "Mr. Steve Pitcher"
And I add "10" voter names
And I cast a vote from "10" voters for "Mr. Steve Pitcher"
Then the "10" voters must have registered a vote
And I close the vote
Then the results must be presented correctly
| candidate         | votes |
| Mr. Steve Pitcher | 10    |

Scenario: Can run an election with ten voter two candidates
Given I have voting booth service
And I have candidate service
And I have voter service
And I have townhall service
When I add candidate "Mr. Steve Pitcher"
And I add candidate "Mrs. Emma Dummer"
And I add "10" voter names
And I cast a vote from "5" voters for "Mr. Steve Pitcher"
And I cast a vote from "5" voters for "Mrs. Emma Dummer"
Then the "10" voters must have registered a vote
And I close the vote
Then the results must be presented correctly
| candidate         | votes |
| Mr. Steve Pitcher | 5     |
| Mrs. Emma Dummer  | 5     |