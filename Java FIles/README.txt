How to run the MDP solver + I/0:

What you need installed:
  -Maven

Initial Step:
  $ cd MDP

To compile the project:
  $ mvn package

To run the project:
  $ mvn exec:java

Program IO:

  inputMap.txt
    -Contains the string version of the map for the MDP
    -The string is broken into two parts "<map-values> <start-position>"
    -The map values are made up of a list of ints where:
      0: Denotes a 45 degree slice of the FOV with no clutter
      1: Denotes a slice of FOV with clutter
      2: Denotes the goal position with no clutter
      3: Denotes the goal position with clutter
    -The map values are 8 numbers from the state-possibilities separated by commas
      Ex. 2,0,1,0,0,1,0,1
    -The start position is an int from 0 to 7 denoting the starting position of the view
    -The map only needs to be input once for all possible states to be computed(aka only one input per frame)

  QTable.txt
    -Contains the output of the Q function
    -Each line is broken up into <state> <action> <q-value>
    -Each state is paired with the optimal action according to calculated q-values
    -The maximum possible q-value is 50
