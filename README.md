# SoPra FS20 - Group 11 - Just One (Server)

## Introduction
The game “Just One” is developed in accordance with the game’s rules, as a web application. It will have the following features:

- Users can create accounts, which will be visible to other users. They are also able to change their name and username.
- A global score system, where users can compare their score over time. It has a search function, wherein users are able to find each other via username.
- The 550 words from the original game are randomly assigned to a card whenever a new game is created. That way, the game offers more variety and new words can smoothly be added.
- A time tracker for certain user actions. Faster clue giving and guessing will increase the amount of points all of the users get at the end of the game. This amount will be individually modified by how many correct guesses and duplicate clues each user has submitted.
- Clues will, in a first step, be automatically checked by a language processing unit or clue checker. This is where the API service of Datamuse https://www.datamuse.com/api/ comes into play. The unit checks for multiple words, duplicates, singular/plural forms, stemming and homophones.


## Technologies
- Java
- Gradle
- Spring Boot
- JUnit 5
- Mocktio
- SonarQube
- We used Datamuse.com as an external API to get homophones, in order to censor invalid clues.

## High-Level Components
- User: Contains all inforamtion about a user like username and score.
- Game: Contains all information about a game like realtions to other entities like cards and clues, or the the state of the game.
- Both User and Game have their own controller and service class.
- ClueChecker: Checks the validity of clues, with the use of the external datamuse API. This class gets called by the Gameservice class.
## Launch & Deployment
- For developement we used Intelij
- The server can be run locally with gradle: gradle bootRun
- The server will run at: localhost:8080/
- We used Heroku to deploy our project.
- Postman is very useful for testing.

### Setup

Download your IDE of choice: (e.g., [Eclipse](http://www.eclipse.org/downloads/), [IntelliJ](https://www.jetbrains.com/idea/download/)) and make sure Java 13 is installed on your system.

1. File -> Open... -> SoPra Server Template
2. Accept to import the project as a `gradle project`

To build right click the `build.gradle` file and choose `Run Build`

You can use the local Gradle Wrapper to build the application.

Plattform-Prefix:

-   MAC OS X: `./gradlew`
-   Linux: `./gradlew`
-   Windows: `./gradlew.bat`

#### Build

```bash
./gradlew build
```

#### Run

```bash
./gradlew bootRun
```

#### Test

```bash
./gradlew test
```

#### Development Mode

You can start the backend in development mode, this will automatically trigger a new build and reload the application
once the content of a file has been changed and you save the file.

Start two terminal windows and run:

`./gradlew build --continuous`

and in the other one:

`./gradlew bootRun`

If you want to avoid running all tests with every change, use the following command instead:

`./gradlew build --continuous -xtest`


#### Debugging

If something is not working and/or you don't know what is going on. We highly recommend that you use a debugger and step
through the process step-by-step.

To configure a debugger for SpringBoot's Tomcat servlet (i.e. the process you start with `./gradlew bootRun` command),
do the following:

1. Open Tab: **Run**/Edit Configurations
2. Add a new Remote Configuration and name it properly
3. Start the Server in Debug mode: `./gradlew bootRun --debug-jvm`
4. Press `Shift + F9` or the use **Run**/Debug"Name of your task"
5. Set breakpoints in the application where you need it
6. Step through the process one step at a time


## Roadmap

- Our parser currently checks for homophones, plurals, and if a clue is contained in the chosenWord or vice versa. A possible improvement could be to search for more categories, e.g. word stem, or to use additional API's to get more words.

## Authors and Acknowledgement
Marino Schneider, Md Rezuanual Haque, Michael Brülisauer, Patrick Reich, Vichhay Ok, Venusan Velrajah
## License
MIT License

Copyright (c) 2020 SoPra-Group-11

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
