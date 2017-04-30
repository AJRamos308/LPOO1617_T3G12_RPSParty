# LPOO1617_T3G12_RPSParty
Repository created for the final project of the "Laboratório de Programação Orientada por Objectos" class - LPOO

# RPS PARTY
## Behavioural Aspects
![Behavioural Aspects](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/StateMachine.PNG)
## UML
![UML](https://github.com/AJRamos308/LPOO1617_T3G12_RPSParty/blob/master/Delivery%20Images/UML.PNG)
## Design Patterns
**Singleton**: Assegura que uma classe terá apenas umas instancia, ou seja, será apenas criada uma e uma só vez, e é acedida de uma forma global. No nosso projeto usaremos para instanciar apenas uma vez o modelo (model) e logica (controller) do jogo principal (em que se escolhe um dos elementos pedra, papel ou tesoura) e de cada mini jogo.

**State**: Permite que um objeto altere o seu comportamento consoante o contexto em que se encontra. Aqui teremos diferentes states para cada mini jogo ao usarmos dentro de um Screen mais do que um Stage diferente.

**Template Method**: Define a estrutura base (esqueleto) de um algoritmos, tendo ligeiras variâncias entras as subclasses. As subclasses redefinem certos passo/métodos da template sem alterarem a estrutura da mesma. No nosso caso tal é visível nas classes abstratas EntityModel, EntityActor, EntityButton, etc, em que teremos algumas características em comum entre as subclasses que derivam destas classes abstratas, no entanto as mesma continuam a ser “únicas” porque redefinem métodos de forma diferente.
## Tests
**1.**To the correct transition between screens, depending on the button pressed;

**2.**To the correct attribution of points to the winner player;

**3.**To the game’s physic (correct objects’ trajectory taking into account their weight, the clash between two objects whose coordinates intersect, etc.);

**4.**To the correct socket creation nad correct connection of the “cliente” (second player) to the socket created by the “server” (first player);

**5.**To the mobile phone’s shake correct detection.

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
