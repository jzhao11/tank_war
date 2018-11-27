1. Version of Java
The version of Java used to build the game is 1.8.0_101.

2. Integrated Development Environment (IDE)
The IDE for Java is NetBeans 8.2 on Windows System.

3. Current Working Directory
The current working directory used for the game is this repo.

4. How to Run the Game
i). Clone the repo with the command: git clone https://github.com/jzhao11/tank_war_game.
ii). Open the NetBeans IDE. Import the Project by clicking "File->New Project".
iii). Choose "Java Project with Existing Sources" in the "Projects" choice box and click "Next".
Specify a name (e.g. csc413_tank_jzhao11) and location for the new project and click "Next".
iv). Click "Add Folder" next to the "Source Package Folder" choice box.
In the pop-up, find and choose the local "csc413-tankgame-jzhao11" repo.
Click "Open" and the repo path will appear in the upper box. Then click "Next".
v). All the *.java files and related resources in the "Included Files" box are needed.
Click "Finish" to finish importing the project.
vi). To build the project, right click the imported project in the top-left "Project" box, and choose "Clean and Build".
This can also be done by clicking the "Clean and Build Project" button (Shift+F11) on the top navigation bar.
vii). To run the game, choose the imported project and click the "Run Project" button (Ctrl+F11) on the top navigation bar.

5. Controls for Playing the Game
Tank1: W -> moving forwards; S -> moving backwards; A -> rotating left; D -> rotating right; Space -> opening fire.
Tank2: Up -> moving forwards; Down -> moving backwards; Left -> rotating left; Right -> rotating right; Enter -> opening fire.
Each tank has 100 health points, 5 basic damage points for each bullet, and 3 lives. When a tank runs out of its lives, the other tank wins.

6. Powerups in the Game
There are 3 types of powerups (extra damage, extra life, and health healing), which are timely generated.
After a specific time interval, the method for creating new powerups will be triggered.
Every time a pair of powerups will be generated, with random positions.
