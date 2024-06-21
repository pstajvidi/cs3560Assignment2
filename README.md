## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

# cs3560Assignment2
 
The `src` folder contains these files for the program: 

- `Driver`: This file will be the run the program, and the only file that has the main
- `AdminControlPanel`: Create the programs java swing set up, as will as using the other files to make the program functional, it also uses a singleton desing strucutre, and will have only one instantiation
- `Observer`: Interface for creating observors and notifying whent there are changes and updates
- `Observable`: Creates a list of observors and contains funtions that add, remove or notify when there are changes
- `UserComponenet`: Uses Obeservable, to act as a base class for User and User group
- `User`: Represents individual users, and extends from user component
- `UserGroup`: Represents Groups, where user can be entered to, also extends from UserComponent
- `UserView`: Creates the UI for the individual users to post messages and follow eachother
- `UserDatabase`: Acts as a repository to manage the users
- `Visitor`: Interface for visitor classes
- `PositiveWordsVisitor`: Extends from Visitor, and calculates the statisitcs for number of users, number of groups and the amount of positive words s
