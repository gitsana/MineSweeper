# MineSweeper

This game can be played in 2 ways:
 1. As a user, from the terminal with prompts
 2. As a solver, which automatically plays 100,000 games in an optimal way and outputs results into the console

**1 - Playing Minesweeper as a user from the terminal**
- Run the program "MainPlayer.java", making sure all classes and enums are present
- Follow the terminal commands to play the game

**2 - Making the Solver play Minesweeper**
- Run the program "MainSolver.java", making sure all classes and enums are present
- The solver will solve Minesweeper 100,000 times, and when it's done will output how many of those games were won and the time elapsed from start to finish

**Clarification & Notes**
- Minesweeper attempts differ to Solver attempts - a Minesweeper attempt (which output to console in player games) defines an "attempt" as uncovering a single cell,
whereas a Solver attempt is one game that was attempted
- I began the first iteration with a simple Solver that only randomly selected cells to uncover without any optimization, and designed the "Minesweeper.startGame" function
so both Solver and Player could use it. When I optimized for Solver I created its own function "Minesweeper.startSolverGame()"; however, the Minesweeper.startGame can still take
a parameter of a Solver player, and if you play it that way, you can compare the optimzied version to the randomized version. To do this, in Solver.java, simply uncomment
"minesweeper.startGame(PlayerType.SOLVER)" and comment out "minesweeper.startSolverGame()", to compare the success rate.
