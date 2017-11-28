parent(bart,homer).
parent(bart,marge).
parent(lisa,homer).
parent(lisa,marge).
parent(maggie,homer).
parent(maggie,marge).
parent(homer,abraham).
parent(herb,abraham).
parent(tod,ned).
parent(rod,ned).
parent(marge,jackie).
parent(patty,jackie).
parent(selma,jackie).
female(maggie).
female(lisa).
female(marge).
female(patty).
female(selma).
female(jackie).
male(bart).
male(homer).
male(herb).
male(burns).
male(smithers).
male(tod).
male(rod).
male(ned).
male(abraham).

%rules
brother(X,Y):- parent(X,Z),parent(Y,Z),male(Y),\+(X=Y).
sister(X,Y):- parent(X,Z),parent(Y,Z),female(Y),\+(X=Y).
aunt(X,Y):- parent(X,Z),sister(Z,Y),\+(X=Y).
uncle(X,Y):- parent(X,Z),brother(Z,Y),\+(X=Y).
grandfather(X,Y):- parent(X,Z),parent(Z,Y),male(Y).
granddaughter(X,Y):- parent(Z,X),parent(Y,Z),female(Y).
ancestor(X,Y):- parent(X,Y).
ancestor(X,Y):- parent(X,Z), ancestor(Z,Y).
descendant(X,Y):- parent(Y,X).
descendant(X,Y):- parent(Z,X),descendant(Y,Z),\+(X=Y).
unrelated(X,Y):- \+ancestor(X,Y), \+descendant(X,Y), \+brother(X,Y), \+brother(Y,X), \+sister(X,Y), \+sister(Y,X), \+aunt(X,Y), \+aunt(Y,X), \+uncle(X,Y), \+uncle(Y,X).