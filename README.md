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
|-- view/
|   |-- GuiContoroller
|   |-- Main
|   |-- GameOverPanel
|   |-- NotificationPanel
|-- model/
|   |-- Board
|   |-- BrickRotator
|   |-- ClearRow
|   |-- DownData
|   |-- MatrixOperations
|   |-- NextShapeInfo
|   |-- Score
|   |-- SimpleBoard
|   |-- ViewData
|   |--bricks/
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
|-- controller/ 
|   |-- EventSource
|   |-- EventType
|   |-- GameController
|   |-- InputEventListener
|   |-- MoveEvent
`````

---

## TODO (Modification)
- [ ] Game Over logic (not high enough)

---

## TODO (Should Implement)
- [ ] **Setting Screen (adjust volume, change key-binds)**
- [ ] **Game Mode: Multi-Level (speed, difficulty)**
- [ ] **Next Block Forecast**
- [ ] **High Score**
- [ ] **Pause/Resume function**
- [ ] **Sound Effect/BGM**
- [ ] **Custom Skin/Theme**
- [ ] **Drop Position Forecast (Ghost Piece)**  