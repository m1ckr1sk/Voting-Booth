# Voting Booth

[![CircleCI](https://circleci.com/gh/m1ckr1sk/voting_booth.svg?style=svg)](https://circleci.com/gh/m1ckr1sk/voting_booth)

An service to provide a system to count votes from multiple voting booths for multiple candidates.  The service must offer management of the voting and provide some resilience, confidence in the result and allow for distributed operation. 

The services are currently spring boot rest services. It uses the Eureka service discovery with a fixed port.

![alt text](images/voting.png "Voting Overview")

## Booth
This service takes votes for a particular candidate. The booth can validate the voter and candidate using the voting and candidate services.  It finds these services using the discovery service.

## Candidate
This service manages the candidates that are available to vote for.

## Voter
This service manages the voters that are elegible to vote and records when they have voted.

## Discovery
The discovery service used for service registration.  Crucially, this allows the booth to find the candidate and voter services.
