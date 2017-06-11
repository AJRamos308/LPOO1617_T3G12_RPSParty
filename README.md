# LPOO1617_T3G12_RPSParty
Repository created for the final project of the "Laboratório de Programação Orientada por Objectos" class - LPOO

# RPS PARTY
## Behavioural Aspects
![Behavioural Aspects](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/StateMachine.PNG)
## UML
![UML](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/UML.PNG)
## Design Patterns
**Singleton**: Ensure a class has only one instance, and provide a global point of access to it. In our project we use it in the controller and model classes.

**State**: Allow an object to alter its behavior when its internal state changes. The object will appear to change its class. In our project we use on GameScreen because we have a stage when the players have to choose an option and then another stage that will draw the result of the match between the players' choices. So, in the same class, the methods can change their behaviour.

**Template Method**:Define the skeleton of an algorithm in an operation, deferring some steps to client subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure. In our project we use it in the abstract classes EntityModel, EntityView and EntityButton.

**Observer**: Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically. Encapsulate the core (or common or engine) components in a Subject abstraction, and the variable (or optional or user interface) components in an Observer hierarchy. In our project we use it as we apply the Model-View-Controller architecture pattern and, consequently, the View corresponds to the Observer.

## Major Difficulties Along the Way
**-** Code structure planning;

**-** Communication between sockets;

**-** Adjust the textures to the right coordinates on the screen;

## Lessons Learned
**-** Do a lot of research  as new concepts come up;

**-** See sample code extracts as a good way to learn;

**-** Write the plan on paper before starting coding;

**-** Ask for help, but always try to resolve it by yourself at first.


## Overall Time Spent Developing
Around 120 hours.

## Work Distribution Amongst Team Members
50-50

## Tests
**1** To the correct transition between screens, depending on the button pressed;

**2** To the correct attribution of points to the winner player;

**3** To the game’s physic (correct objects’ trajectory taking into account their weight, the clash between two objects whose coordinates intersect, etc.);

**4** To the correct socket creation nad correct connection of the “cliente” (second player) to the socket created by the “server” (first player);

**5** To the mobile phone’s shake correct detection.

## Mock App
![Main Menu](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/MainMenu.png)

![Join](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/JoinRoom.png)

![Create](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/CreateRoom.png)

![Choices](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/RockPaperScissors.png)

![Create](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/CreateRoom.png)

![Fight](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/FightingScene.png)

![Rock](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/CastleCrusher.png)

![Paper](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/PaperTie.png)

![Scissors](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/Symmetry.png)

![Win](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/WinningLosingScreen.png)

Afonso Jorge Moreira Maia Ramos     up201506239@fe.up.pt

Beatriz Souto de Sá Baldaia         up201505633@fe.up.pt
