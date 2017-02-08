Feature: File checking
Rows comparison with given text in examples

Scenario Outline: Find file and compare its rows with examples

Before Find configuration file
Given Find address in configuration file
When We can read testing file
Then Check row <rowNum> for <text> equals

Examples:
|rowNum|text|
|1|qwerty|
|6|123-$/*|
|10||
|20|rytryt|