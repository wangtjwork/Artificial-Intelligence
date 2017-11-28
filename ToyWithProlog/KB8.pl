twoInARow(P,R):- p(P,R,1),p(P,R,2).
twoInARow(P,R):- p(P,R,1),p(P,R,3).
twoInARow(P,R):- p(P,R,2),p(P,R,3).
twoInACol(P,C):- p(P,1,C),p(P,2,C).
twoInACol(P,C):- p(P,1,C),p(P,3,C).
twoInACol(P,C):- p(P,2,C),p(P,3,C).
twoInADiag(P,3,3):- p(P,1,1), p(P,2,2).
twoInADiag(P,1,1):- p(P,3,3), p(P,2,2).
twoInADiag(P,2,2):- p(P,1,1), p(P,3,3).
twoInADiag(P,2,2):- p(P,1,3), p(P,3,1).
twoInADiag(P,1,3):- p(P,2,2), p(P,3,1).
twoInADiag(P,3,1):- p(P,1,3), p(P,2,2).

blank(R,C):- \+p(x,R,C), \+p(o,R,C).
canWin(P,R,C):- twoInARow(P,R), blank(R,C),!.
canWin(P,R,C):- twoInACol(P,C), blank(R,C),!.
canWin(P,R,C):- twoInADiag(P,R,C), blank(R,C),!.

theOther(x,o).
theOther(o,x).

forcedMove(P,R,C):- theOther(P,Q), twoInARow(Q,R),blank(R,C),!.
forcedMove(P,R,C):- theOther(P,Q), twoInACol(Q,C),blank(R,C),!.
forcedMove(P,R,C):- theOther(P,Q), twoInADiag(Q,R,C), blank(R,C),!.

assign(1,1).
assign(1,2).
assign(1,3).
assign(2,1).
assign(2,2).
assign(2,3).
assign(3,1).
assign(3,2).
assign(3,3).

ttt_move(P,R,C):- assign(R,C), canWin(P,R,C), write('go for win!~n'), nl, format('R = ~d, C = ~d~n',[R,C]),!.
ttt_move(P,R,C):- assign(R,C), forcedMove(P,R,C), write('move to block opponent!'), nl, format('R = ~d, C = ~d~n',[R,C]),!.
ttt_move(_,R,C):- assign(R,C), blank(R,C), write('any valid move!'), nl, format('R = ~d, C = ~d~n',[R,C]),!.

% assert these facts as the state description
p(x,1,1).
p(o,3,1).