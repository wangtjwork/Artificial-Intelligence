divisible(N,X):- M is N mod X, M=0.

factor(Number,List):- factorize(Number, List, 2).
    
factorize(1,[],_):- !.

factorize(Number, [LowestFactor|Next], LowestFactor):- 
    divisible(Number,LowestFactor), !, Res is Number // LowestFactor, factorize(Res, Next, LowestFactor).
factorize(Number, List, 2):-
    factorize(Number, List, 3).
factorize(Number, List, LowestFactor):-
    NextFactor is LowestFactor + 2, Number >= NextFactor, !, factorize(Number, List, NextFactor).