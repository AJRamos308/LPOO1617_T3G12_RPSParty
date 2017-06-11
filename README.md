# RPS PARTY Final Delivery
[![BCH compliance](https://bettercodehub.com/edge/badge/AJRamos308/LPOO1617_T3G12_RPSParty?branch=master&token=fe6c87c41045ba638cc2f32723fe1092a95db246)](https://bettercodehub.com/)
## Setup
Install the .apk file provided which is located inside the android folder and is named "android-release.apk" or through the Play Store:
[Play Store](https://play.google.com/store/apps/details?id=com.rpsparty.game)
## Development Documentation
### Updated UML

![UML](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/NewUML.PNG)

(In more detail inside the file UML.pdf located in the root.)

### Design Patterns
**Singleton**: Ensure a class has only one instance, and provide a global point of access to it. In our project we use it in the controller and model classes.

**State**: Allow an object to alter its behavior when its internal state changes. The object will appear to change its class. In our project we use on GameScreen because we have a stage when the players have to choose an option and then another stage that will draw the result of the match between the players' choices. So, in the same class, the methods can change their behaviour.

**Template Method**:Define the skeleton of an algorithm in an operation, deferring some steps to client subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure. In our project we use it in the abstract classes EntityModel, EntityView and EntityButton.

**Observer**: Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically. Encapsulate the core (or common or engine) components in a Subject abstraction, and the variable (or optional or user interface) components in an Observer hierarchy. In our project we use it as we apply the Model-View-Controller architecture pattern and, consequently, the View corresponds to the Observer.

### Major Difficulties Along the Way
* Code structure planning;

* Communication between sockets;

* Adjust the textures to the right coordinates on the screen.

### Lessons Learned
* Do a lot of research  as new concepts come up;

* See sample code extracts as a good way to learn;

* Write the plan on paper before starting coding;

* Ask for help, but always try to resolve it by yourself at first;

* Collaborating with people from others courses to help with the design of the app.


### Overall Time Spent Developing
Around 120 hours.

### Work Distribution Amongst Team Members
50-50

## User Manual
Splash Screen.

![SplashScreen](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/SplashScreen.png)

You can choose to start a Party (game) or to join one.

![MainMenuS](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/MainMenuS.png)

If you have pressed “START PARTY” you IP is shown and you also need to choose the number of sets that your game will have.

![StartParty](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/StartParty.png)

If you have pressed “JOIN PARTY” you have to insert your opponent IP to join his party (game).

![JoinParty](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/JoinParty.png)


![ServerIP](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/ServerIP.png)

As the game starts you have to choose between rock, paper or scissors (one of the three buttons).

![RPS](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/RPS.png)

After you and your opponent choose one option, you have to shake your phone 3 times so yours options are launched in both screens (and then collide).

![collision](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/collision.png)

If you win a set you are marked with X, otherwise you are marked with a check mark.

![Results](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/Results.png)

If it’s a tie, you have to play a thematic mini game against you opponent.
Rock Mini Game:
Tap the rocks to destroy them. They can be empty or have a coin. Earn as much coins as you can (each coin is one point).

![RockGame](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/RockGame.png)

Paper Mini Game:
Drag off a roll of toilet paper

![PaperGame](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/PaperGame.png)

Scissors Mini Game: Draw the symmetry as accurately as possible.

![ScissorsGame](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/ScissorsGame.png)

Every mini game has a limited time to be concluded. You can also see a quick explanation of the mini games in the Main Menu if click on the button in the upper right corner:

![Help](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/Help.png)




# RPS PARTY Intermediate Checkpoint
## Behavioural Aspects
![Behavioural Aspects](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/StateMachine.PNG)
## UML
![UML](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/UML.PNG)
## Design Patterns
**Singleton**: Ensure a class has only one instance, and provide a global point of access to it. In our project we use it in the controller and model classes.

**State**: Allow an object to alter its behavior when its internal state changes. The object will appear to change its class. In our project we use on GameScreen because we have a stage when the players have to choose an option and then another stage that will draw the result of the match between the players' choices. So, in the same class, the methods can change their behaviour.

**Template Method**:Define the skeleton of an algorithm in an operation, deferring some steps to client subclasses. Template Method lets subclasses redefine certain steps of an algorithm without changing the algorithm's structure. In our project we use it in the abstract classes EntityModel, EntityView and EntityButton.

**Observer**: Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically. Encapsulate the core (or common or engine) components in a Subject abstraction, and the variable (or optional or user interface) components in an Observer hierarchy. In our project we use it as we apply the Model-View-Controller architecture pattern and, consequently, the View corresponds to the Observer.

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
