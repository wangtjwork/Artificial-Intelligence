board(1,1).
board(1,2).
board(1,3).
board(1,4).
board(2,1).
board(2,2).
board(2,3).
board(2,4).
board(3,1).
board(3,2).
board(3,3).
board(3,4).
board(4,1).
board(4,2).
board(4,3).
board(4,4).

neighborsUp(C,R, Cup, Rup):- Temp is R + 1, Temp > 0, Temp < 5, Cup is C, Rup is Temp.
neighborsDown(C,R, Cdown, Rdown):- Temp is R - 1, Temp > 0, Temp < 5, Cdown is C, Rdown is Temp.
neighborsLeft(C,R, Cleft, Rleft):- Temp is C - 1, Temp > 0, Temp < 5, Cleft is Temp, Rleft is R.
neighborsRight(C,R, Cright, Rright):- Temp is C + 1, Temp > 0, Temp < 5, Cright is Temp, Rright is R.

candidate(C1,R1):- visited(C,R), neighborsUp(C,R,C1,R1), \+visited(C1,R1).
candidate(C1,R1):- visited(C,R), neighborsDown(C,R,C1,R1), \+visited(C1,R1).
candidate(C1,R1):- visited(C,R), neighborsLeft(C,R,C1,R1), \+visited(C1,R1).
candidate(C1,R1):- visited(C,R), neighborsRight(C,R,C1,R1), \+visited(C1,R1).

noWumpus(X,Y):- neighborsUp(X,Y, C1, R1), \+stench(C1,R1), visited(C1,R1).
noWumpus(X,Y):- neighborsDown(X,Y, C1, R1), \+stench(C1,R1), visited(C1,R1).
noWumpus(X,Y):- neighborsLeft(X,Y, C1, R1), \+stench(C1,R1), visited(C1,R1).
noWumpus(X,Y):- neighborsRight(X,Y, C1, R1), \+stench(C1,R1), visited(C1,R1).

noPit(X,Y):- neighborsUp(X,Y, C1, R1), \+breeze(C1,R1), visited(C1,R1).    
noPit(X,Y):- neighborsDown(X,Y, C1, R1), \+breeze(C1,R1), visited(C1,R1). 
noPit(X,Y):- neighborsLeft(X,Y, C1, R1), \+breeze(C1,R1), visited(C1,R1). 
noPit(X,Y):- neighborsRight(X,Y, C1, R1), \+breeze(C1,R1), visited(C1,R1). 

safe(X,Y):- board(X,Y), noWumpus(X,Y), noPit(X,Y).

move(X,Y):- board(X,Y), candidate(X,Y), safe(X,Y).

visited(4,1).
visited(4,2).
visited(4,3).
visited(4,4).
stench(4,2).
breeze(4,3).
breeze(4,4).