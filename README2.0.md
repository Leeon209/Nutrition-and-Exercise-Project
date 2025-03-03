# TERM-PROJECT: Team Dakota

An online application to be developed in Java 11=>**17** for the purpose of...  >**Health!**<

##  Team Dakota Team & Roles

- Daniel McKee, Team Coordinator

- Leeon Noun, Quality Coordinator

- Sahand Nowshiravani - Design Coordinator

- Kyle Weishaar - Requirements Coordinator

- Luke Picciano - Configuration Manager

## Team Additional Information

- Meeting times: Monday 5pm - 7pm, Friday 3pm - 4:15pm

- Google Drive link -> https://drive.google.com/drive/folders/1jK_ScWB3ooWHG0kst8kqjpRxXCyG_zEq?usp=sharing

- Trello link -> https://trello.com/b/XlMMbMJq/2241-swen-383-01-1a-team-dakota

##Prerequisites

- Java **17=>21**

- VSCode IDE - as developer platform

#

##How to begin

1. Clone the repository and go to the root directory on your local machine.

2. Execute the defaultMain.HealthApplication.java file which starts the application.

3. View the Graphical User Interface to interact with application.

[CREATE A FOOD ITEM]
1. Select the Create Food button in the Food management section
2. Insert all possible values for the food item, and submit.

[VIEW FOOD ITEM]
1. Insert name of food item and submit to view.

[VIEW RECIPE ITEM]
1. Insert name of recipe and submit to view.

[VIEW LOG ITEM]
1. Insert date of log item

[VIEW LOG BAR GRAPH]
1. Select the View Log option and select a date of your choosing
2. After confirming, and viewing the stats of the log, navigate to the Nutrition Bar Graph at the top of the window to see a breakdown.

[SAVE INFO/DATA]
1. Select the respective Save Data button in each section to save the data


[PHASE V2.0 DESCLAIMERS]
Phase 1 of the project is fully functional in all aspects.
Refactoring was done from feedback given during V1.0. 

Some issues that may be noticed:
1. Our Observer pattern may be incomplete. That's the one issue we should have fixed but was not able to get around to it unfortunately. I think some minor changes could fix this pretty quickly but with how the current program is set up, trial and error would be needed and time is not on our side. 
2. When interacting with some commands, the user may be given a redundant prompt 3 to 4 times before finally displaying the correct information. My hunch is that it has to do with the observer, which is odd. Maybe the Observer classes directly interact with AWT?
3. Some button actions are not fully implemented. This was more of a pick and choose which functions were important to make sure they worked   with the GUI. Time constraints were the issue here.

##License
MIT License

See LICENSE for details.
