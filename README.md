# Minesweeper
### Rules of Minesweeper

Minesweeper is a grid of tiles, each of which may or may not cover hidden mines. The goal is to click on every tile except those that have mines. 

When a user clicks a tile, one of two things happens. If the tile was covering a mine, the mine is revealed and the game ends in failure. If the tile was not covering a mine, it instead reveals the number of adjacent (including diagonals) tiles that are covering mines – and, if that number was 0, behaves as if the user has clicked on every cell around it. 

When the user is confident that all tiles not containing mines have been clicked, the user presses a Validate button (often portrayed as a smiley-face icon) that checks the clicked tiles: if the user is correct, the game ends in victory, if not, the game ends in failure.

### Implemented Funtions

The board is an 8x8 grid and by default 10 hidden mines are randomly placed into the board.

The app supports these functions:
* New Game: start a new, randomly generated game.
<img src="https://github.com/chenglongwei/minesweeper/blob/master/screenshort/new_game.png" width="120">

<img src="https://github.com/chenglongwei/minesweeper/blob/master/screenshort/hitting_a_mine.png width="120">

* Validate: check that a user has correctly marked all the tiles and end the game in either victory or failure.
<img src="https://github.com/chenglongwei/minesweeper/blob/master/screenshort/validate.png" width="120">

* Cheat: in any manner you deem appropriate, reveal the locations of the mines without ending the game.
<img src="https://github.com/chenglongwei/minesweeper/blob/master/screenshort/cheat_model.png width="120">

* Saving/Loading game state.
<img src="https://github.com/chenglongwei/minesweeper/blob/master/screenshort/level_choice_save_load.png" width="120">

* Changing difficult level.
* Changing the size of the board.
<img src="https://github.com/chenglongwei/minesweeper/blob/master/screenshort/custom_level.png" width="120">

### Build and Install
To build the project, you can use **Android Studio** to open the project and then execute **gradle build** in the root directory.

To install the app, execute **gradle installDebug** or **gradle installRelease**.

Or else, there are two apks in the root directory. 

You can execute **adb install -r minesweeper-debug.apk** or **adb install -r minesweeper-release.apk**
