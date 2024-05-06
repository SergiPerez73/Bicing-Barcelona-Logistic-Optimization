# Bicing-Barcelona-Logistic-Optimization
This project implements a logistics optimization, simulating a city where you have different stations, and you need to be as close a possible to the demand of bikes in each one. You specify the number of trips done to transport bikes, taking its cost into account.

## Code execution

To run the project, the folder contains a folder called `codigo_fuente` contains a `JavaApplication1` which is a NetBeans 12.4 project.
Once you open it with NetBeans version 12.4 or higher, you must add to Libraries all the .jar libraries found in the `lib` folder.

Finally, you can run the project by running the project in the `Main` file of the package `IA.practica`.

## Detailed explanation of the project
In this project, we generate cities with different number of stations in different points of a map. Each city has a seed number, and you can generate a random city by introducing a random number in the seed when you generate it. Each station also contains the number of bikes that has at the start and a prediction of the number of bikes that should have for the next hour, taking into account the demand. The more bikes we have in a station until we reach the demand, the better. 
To transport bikes, we can select as many vans as we want and transport bikes from one station to another on a trip. Each trip has a starting point, a destination where we leave a certain number of bikes, and an optional second destination. Each van can only do one trip, and we take into account the cost of the trips on the basis of the travelled distance and the number of bikes that are inside the van (which are 30 at most).

## Algorithms

As finding an optimal solution to this problem is very complex, we use instead artificial intelligence algorithms where we generate an initial solution and we modify this solution with some operators to search for a better solution.
In Hill Climbing algorithm, we modify the solution with the best operator possible for each case until we reach a point where we can't improve with our operators.
In Simulated Annealing, we modify the solution with a random operator out of the ones we have, and we continue with this generated solution if it improves. In some randomly selected cases, we can also continue if it doesn't improve, but this becomes more unlikely as the time passes.

## Change parameters

To change the parameters of the program, you must enter the program Main.java
and modify the values of the following variables:

1. numbicis: is the number of bicycles.
2. numestacions: the number of stations
3. seed: the random seed with which the stations will be generated
4. numfurgonetas: the number of vans
5. horapunta: if true, we will generate a peak hour scenario. If false, balanced.
6. initialgenerator: 0 for the first generator, 1 for the second, 2 for the third. The generators are explained in the documentation.
7. hillclimbing: if true, will use Hill Climbing. If false, if false Simulated Annealing.

## Detailed explanation
The `Documentación Práctica Búsqueda Local.pdf` file contains a more detailed explanation of this project in Spanish.

## Authors
Sergi Pérez Escalante (Code and tests)
Diego Velilla Recio (Tests)
Pablo Buxó Hernando (Tests)
