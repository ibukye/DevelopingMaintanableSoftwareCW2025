# COMP2042 Coursework - Tetris Maintenance and Extension
 
---

## GitHub Repository
[https://github.com/ibukye/DevelopingMaintanableSoftwareCW2025](https://github.com/ibukye/DevelopingMaintanableSoftwareCW2025)

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