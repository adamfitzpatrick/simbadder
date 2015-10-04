Feature: Simbadder HATEOAS

	Scenario: Acquire data related to Sirius
		When I retrieve data on the star Sirius
		Then I can obtain a unique self-reference link to Sirius