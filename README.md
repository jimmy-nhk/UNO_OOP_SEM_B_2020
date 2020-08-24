RMIT University Vietnam \
Course: *INTE2512 Object-Oriented Programming* \
Semester: *2020B* \
Assessment name: *Final Project* \
Team name: *UNO* \
Team members: 
1. Nguyen Dang Huynh Chau - s3777214
2. Nguyen Hoang Khang - s3802040
3. Nguyen Dinh Dang Nguyen - s3759957
4. Bui Thanh Huy - s3740934
5. Nguyen Phuoc Nhu Phuc - s3819660

# INTRODUCTION
This is a Java-based GUI program that simulates the real-life card game called UNO with a user-friendly interface. The program has two main screens: one for the main menu and one for the main game. The game application can also be manipulated within a multi computer network. UNO is an American card game with 4 players in a match. Players can play with their friends and family or with our built-in bots. Vacant spots in game will be replaced by bots.
At the start of the game, each player has 7 cards. The pile of the other cards are placed facing down. When a card is discarded, the next player has to play a card which has the same number, color or action as the last discarded card. Apart from the cards with numbers, there are also Wild Cards which can carry multiple colors, Wild Draw Two/Four cards which make the next player draw two/four cards from the draw pile and Skip cards which enable the player to skip their turn. If a player does not have any cards with the same color or action as the last one on the discard pile or prefer not to discard even when they have the playable card, they must draw a card at the top of the draw pile. If that card can be discarded, the player can either play it or keep it and wait for the next turn. Other than that, the player can also put forward a Wild Card or Wild Draw Four card. 
# FEATURES
* **Display game and GUI components**: Application is set with visually appealing and user-friendly GUI. The main board is designed to be more appealing than basic game platforms (added more themes and customised nodes). 
* **Player Options**: Before entering the home page, each player is asked to set a name.
* **Multiplayer**: Each match has 4 players competing again each other.
* **Playing with bots**: Players could play with a NPC (non-player character), which is operated by a designed algorithm.
* **Timer**: A timer is designated for each player when it is their turn. When the time goes off and the player has not handed in their card, they will lose their turn.
* **Multi languages**: The game is available in English and Vietnamese. Players are allowed to change the main language in _**Setting &#8594; Language.**_
* **Multi languages**: The game is available in English and Vietnamese. Players are allowed to change the main language in _**Setting &#8594; Language.**_
* **Various themes**: To change to a different background, players would have to go to _**Setting &#8594; Theme.**_
* **Leaderboard**: Names of the players with the highest scores are displayed on the Leaderboard. To see Leaderboard, go to _**Menu &#8594; Leaderboard.**_ 
* **User-friendly Interface**: the game was built with standard and accessible User Interface, easy rules and clear instructions.
* **Networking**: Players are to play together via a multi-computer network.
# Installation
* **Open the project using Intellij IDEA 2019.
* Setup the JDK to version 11.
# Add the JavaFX library
* From the main menu, select File | Project Structure or Ctrl+Shift+Alt+S on the toolbar.
* Specify the path to the lib folder in the JavaFX SDK package, for example: C:\javafx-sdk-11.0.2\lib
* In the Choose Modules dialog, select the FinalASM_OOP module and click OK.
# Add VM options
* From the main menu, select Run | Edit Configurations.
* Select Application | Main from the list on the left. *In the VM options field, specify: --module-path %PATH_TO_FX% --add     modules=javafx.controls,javafx.fxml,javafx.media
* Instead of %PATH_TO_FX%, specify the path to the JavaFX SDK lib directory, for example: C:/javafx-sdk-11.0.2/lib.
# Compile and Run
* Open class Main.java.
* Press Shift + F10 or go to Run tools and hit Run 'Main'.
* To test the Network feature by setting up a localhost, run ServerMain and then run at most 4 Main in parallel mode (can be setup in the Run | Edit Configurations)
# Acknowledgement
* Mr Quang's lecturer slides.
* Package javafx.scene.media : https://docs.oracle.com/javafx/2/api/javafx/scene/media/package-summary.html
