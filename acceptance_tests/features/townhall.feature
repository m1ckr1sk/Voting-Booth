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

Scenario: Can run an election with 1K voter five candidates
Given I have voting booth service
And I have candidate service
And I have voter service
And I have townhall service
When I add candidate "Mr. Steve Pitcher"
And I add candidate "Mrs. Emma Dummer"
And I add candidate "Mr. Paul Enfield"
And I add candidate "Dr. Patricia Hughes"
And I add candidate "Maj. Gail De Gruber"
And I add "1000" voter names
And I cast a vote from "50" voters for "Mr. Steve Pitcher"
And I cast a vote from "385" voters for "Mrs. Emma Dummer"
And I cast a vote from "23" voters for "Mr. Paul Enfield"
And I cast a vote from "397" voters for "Dr. Patricia Hughes"
And I cast a vote from "145" voters for "Maj. Gail De Gruber"
Then the "1000" voters must have registered a vote
And I close the vote
Then the results must be presented correctly
| candidate           | votes |
| Mr. Steve Pitcher   | 50    |
| Mrs. Emma Dummer    | 385   |
| Mr. Paul Enfield    | 23    |
| Dr. Patricia Hughes | 397   |
| Maj. Gail De Gruber | 145   |