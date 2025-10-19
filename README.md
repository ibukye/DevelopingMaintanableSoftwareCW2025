# COMP2042 Coursework - Tetris Maintenance and Extension
 
---

## GitHub Repository
[https://github.com/ibukye/DevelopingMaintanableSoftwareCW2025](https://github.com/ibukye/DevelopingMaintanableSoftwareCW2025)

---


## Table of Contents
- [Implemented and Working Features](#implemented-and-working-features)
        - [High Score Saving and Display](#high-score-saving-and-display)
        - [Pause and Resume Function](#pause-and-resume-function)
        - [Next Block Preview](#next-block-preview)
        - [Level Up and Speed Increase](#level-up-and-speed-increase)
        - [Hard Mode](#hard-mode)
        - [New Game with “N” Key](#new-game-with-n-key)
        - [Speed Bonus Mechanic](#speed-bonus-mechanic)
- [Implemented but Previously Problematic Features](#implemented-but-previously-problematic-features)
- [Unimplemented Features](#unimplemented-features)
- [New Java Classes](#new-java-classes)
        - [view/MainMenuController.java](#viewmainmenucontrollerjava)
        - [model/Difficulty.java](#modeldifficultyjava)
        - [controller/InputHandler.java](#controllerinputhandlerjava)
        - [controller/GameController.java](#controllergamecontrollerjava)
        - [GameConfig.java](#gameconfigjava)
- [Unexpected Issues and Their Solutions](#unexpected-issues-and-their-solutions)
        - [Obstacle Placement Error in Hard Mode](#obstacle-placement-error-in-hard-mode)
        - [non-static method error](#non-static-method-error)
        - [NullPointerException When Loading Icon Images](#nullpointerexception-when-loading-icon-images)






---




## Compilation Instructions

---

## Directory Structure
**MVC Design Pattern (Model-View-Controller)**
- **Model** : Data & Logic
- **View** : GUI
- **Controller** : Update Model & View (in between)

`````
com.comp2042
|-- controller/ 
|   |-- EventSource 
|   |-- EventType
|   |-- GameController
|   |-- InputEventListener
|   |-- InputHandler
|   |-- MoveEvent
|
|-- model/
|   |-- Board
|   |-- BrickRotator
|   |-- ClearRow
|   |-- Difficulty
|   |-- DownData
|   |-- MatrixOperations
|   |-- NextShapeInfo
|   |-- Score
|   |-- SimpleBoard
|   |-- ViewData
|   |-- bricks/
|       |-- Brick
|       |-- BrickGenerator
|       |-- IBrick
|       |-- JBrick
|       |-- LBrick
|       |-- OBrick
|       |-- RandomBrickGenerator
|       |-- SBrick
|       |-- TBrick
|       |-- ZBrick
|
|-- view/
|   |-- GameOverPanel
|   |-- GuiContoroller
|   |-- Main
|   |-- MainMenuController
|   |-- NotificationPanel
|
|-- GameConfig
`````

---

## TODO (Modification)
- [x] Game Over logic (not high enough)
- [x] Display Score
---

## TODO (Should Implement)
- [ ] **Setting Screen (adjust volume, change key-binds)**
- [x] **Game Mode: Multi-Level (speed, difficulty)**
- [x] **Next Block Forecast**
- [x] **High Score**
- [x] **Pause/Resume function**
- [ ] **Sound Effect/BGM**
- [ ] **Custom Skin/Theme**
- [ ] **Drop Position Forecast (Ghost Piece)**

Difficulties
- Easy : No modification
- Normal : Speed will be increased as the player clears a row
- Hard : Normal + Some bricks are placed before it starts

---

## TimeLine

- [x] Directory Refactoring
- [ ] Code Refactoring
- [ ] Code Modification
- [ ] Code Extension


---

### Speed Bonus
When players actively press the down key to accelerate the bricks' descent, they earn extra points
This implements high risk, high return




---


# Implemented and Fully Functional Features

## High Score Saving and Display
When the game is over, if the player’s score exceeds the current high score, it is saved as `tetris_highscore.txt` in the user’s home directory.  
The next time the game starts, the high score is automatically loaded and displayed on the screen.

## Pause and Resume Function
The game can be paused and resumed using either the **Pause/Resume button** or the **SPACE key**.

## Next Block Preview
The block that will appear next is displayed on the right side of the game screen.

## Level Up and Speed Increase
After clearing a certain number of lines, the player levels up, and the falling speed of the blocks increases.  
A notification saying **“SPEED UP!”** appears on the screen.

## Hard Mode
When **Hard** difficulty is selected, random obstacles are generated at the bottom of the board at the start of the game.

## New Game with “N” Key
Pressing the **N key** starts a new game while maintaining the current difficulty and settings, both during gameplay and after a game over.

## Speed Bonus System
When the player actively drops blocks using the **Down Arrow key**, an additional 1 point per line cleared is awarded as a **time-attack bonus**.

---

# Implemented but Previously Malfunctioning Features
Currently, there are **no malfunctioning features**.  
All issues encountered during development have been resolved.  
(Detailed explanations are provided in the [Unexpected Issues](#unexpected-issues) section below.)

---

# Unimplemented Features

## Hard Drop
A function that instantly drops the block to the bottom of the board.  
This feature was omitted due to time constraints and the prioritization of core functionality.

## Ghost Piece
A visual “shadow” that shows where the block will land.  
This was not implemented due to the complexity of its UI rendering.

## Sound Effects
Sound effects for block movement, rotation, and line clearing.  
While they would enhance the user experience, they were considered non-essential and thus given lower development priority.

---

# New Java Classes

## view/MainMenuController.java
**Purpose:** Controller for the main menu screen (`mainMenu.fxml`).  
**Details:** Handles transitions and application termination when buttons (Easy/Hard/Exit) are pressed.  
By separating responsibilities from the main game controller (`GuiController`), each screen now has a clear focus, improving overall code clarity and organization.

## model/Difficulty.java
**Purpose:** Enum type defining game difficulty levels (`EASY`, `NORMAL`, `HARD`).  
**Details:** Using an enum instead of strings or integers prevents invalid difficulty values (e.g., `"Medium"`) from being set, enabling compile-time error detection and improving code robustness and type safety.

## controller/InputHandler.java
**Purpose:** Dedicated class for handling keyboard input.  
**Details:** Previously, key event logic was implemented inside `GuiController`.  
By refactoring it into a separate `InputHandler` class, the program now adheres to the **Single Responsibility Principle (SRP)** — allowing `GuiController` to focus purely on UI updates, and `InputHandler` to handle input logic.  
This greatly improves maintainability.

## controller/GameController.java
**Purpose:** The central controller in the MVC architecture.  
**Responsibilities:**
- **Bridging the View and Model:** Receives user input from the view (`GuiController`, via `InputHandler`) and updates the model (`Board`).
- **State Monitoring:** Detects model changes (block placement, line clear) and instructs the view to refresh accordingly.
- **Persistence:** Manages saving and loading of high scores (`loadScore`, `saveScore`).
- **Game Progression:** Controls the main game loop (`Timeline`) and adjusts speed according to the current level.

## GameConfig
**Purpose:** Centralized management of configuration values and constants (e.g., board size, speed by level).  
**Details:**  
By eliminating “magic numbers” from the code and defining them in one place, changes can be made easily and consistently, improving both readability and maintainability.

---

# Unexpected Issues

## Incorrect Brick Placement in Hard Mode
**Issue:**  
In Hard Mode, gray obstacle bricks appeared at the far-right edge of the screen instead of at the intended positions.  
**Solution:**  
The problem was caused by a logic error in coordinate handling.  
Using explicit numeric values instead of predefined constants resolved the issue.

## “non-static method cannot be referenced from a static context” Error
**Issue:**  
A compilation error occurred when attempting to call `GameController.stopGame()` from `GuiController`.  
**Solution:**  
After understanding the distinction between static and instance methods, the method call was corrected to reference the `GameController` instance (passed via `setEventListener`) rather than the class itself.

## NullPointerException During Icon Loading
**Issue:**  
A `NullPointerException` occurred when loading icon images using `getClass().getResourceAsStream()`.  
**Solution:**  
It was discovered that resource paths must start with a leading `/` when referenced from the `resources` folder.  
Changing the path from `"icons/..."` to `"/icons/..."` fixed the issue.

## The sound isn't played sometimes
