# OOP-virtual-world-java 2021

- This project is a `world simulation` with various organisms with `GUI in Swing library (java)`.

- The aim of the project was to `improve my understanding of OOP`.

- Organisms have different behavior thanks to virtual methods of action and collision.

- Base class Organism and its derived classes: Animal and Plant are examples of polymorphism and inheritance. 

- All variables inside classes are appropriately encapsulated.

## Introduction

In the following document, I will describe the work done during the project entitled "Virtual World" within the object-oriented programming course.

The second project was made in Java, using the Swing library. It was my first real project in this language.

For the functionality, I used: `KeyListener` and `ActionListener`, `JFrame`, `JMenu`, `JMenuItem`, `JButton`, `JPanel`, `JLabel`, and `Image`.

## Completed tasks

The game has:

- Implementation of the game world and its visualization.
  
- Implementation of all mandatory animal species.
  
- Implementation of all plant species.
  
- Implementation of Human moved by keyboard arrows.
  
- Implementation of a special Human skill (antelope speed)
  
- Implementation of the ability to save to a file and load a virtual state from a file world.
  
- Implementation of the ability to add organisms to the game world. Pressing on a free field should give you the opportunity to add any existing in the world organisms.

I divided the project files thematically into `packages`: `plants` and `animals`, which contain .java files - plants and animals, respectively.

I also set the game icon.

All graphical properties of the world are contained in the class and at the same time in the file `WorldSwing.java`

## The game

The game starts with a new player welcome (JLabel):
![obraz](https://github.com/AgnieszkaDelmaczynska/OOP-virtual-world-java/assets/105732925/fe5d5cc2-1013-4025-81e7-5dae9e79a288)

Then the game window is shown with a drop-down menu (`JMenu `and `JMenuItem`):
![obraz](https://github.com/AgnieszkaDelmaczynska/OOP-virtual-world-java/assets/105732925/7227300c-5f1e-45bd-a556-6dfc023aa262)

We choose `nowa gra` (new game):
![obraz](https://github.com/AgnieszkaDelmaczynska/OOP-virtual-world-java/assets/105732925/751166f5-e800-46df-a988-35b90d04528b)

20x20 board and the organisms added to it (including the human) are displayed.

The instructions for using the keys and the information about the turn number are placed in the top right panel.

In the lower section, there is a legend of the organisms' designations with the colors that represent them (JButtons non-functional).

Functional buttons: `Zacznij nowa gre` (Start a new game) and `Wyjdz z gry` (Exit game). When you click on them, using the ActionListener the appropriate action is performed. `Wyjdz z gry` (Exit game) closes the game window completely. 

Unlike the first project in C++, `displaying information` about the current actions on the board had to take place not in the console, but `in the graphical window of the game`.

Class `Informator.java` is responsible for conveniently transferring the information from a component of the Swing library and printing it on the screen.


## The course of the game

![obraz](https://github.com/AgnieszkaDelmaczynska/OOP-virtual-world-java/assets/105732925/fc3fe796-e1a1-4c54-a595-3578896bf7ed)
![obraz](https://github.com/AgnieszkaDelmaczynska/OOP-virtual-world-java/assets/105732925/4bbf503b-8713-4f76-958a-2f4150c9c612)

After clicking on a free field, you can manually add an organism to the board (JList + JScrollPane, so as not to display the entire list at once and thus not cover the board).

Any organism can be added except the human, which is only one.

When a human dies, the game ends.

The Enter key also does not work and does not proceed to the next turn.

![obraz](https://github.com/AgnieszkaDelmaczynska/OOP-virtual-world-java/assets/105732925/4ea57810-14b8-483d-8e7b-835be96049a1)




