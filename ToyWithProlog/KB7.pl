concat([],List, List).
concat([Head|Tail], List, [Head|Res]):- concat(Tail, List, Res).

convert(List, Number):- convert(List, 0, Number).
convert([Head|Tail], A, Number):- !, B is A * 10 + Head, convert(Tail, B, Number).
convert(_, B, N):- N is B.

sum(Add1,Add2,Result) :-
    concat(Add1,Add2,Temp),
    concat(Temp,Result,Chars),
    assign([0,1,2,3,4,5,6,7,8,9],Chars),
    convert(Add1, Num1),
    convert(Add2, Num2),
    convert(Result, NumRes),
    NumRes is Num1+Num2,
    !.



remove(X,[X|Xs],Xs).
remove(X,[Y|Ys],[Y|Res]) :-
    remove(X,Ys,Res).

assign(Digits,[X|Tail]) :-
    nonvar(X),
    !,
    assign(Digits,Tail).
assign(Digits,[X|Tail]) :-
    remove(X,Digits,D1),
    assign(D1,Tail).
assign(_,[]) :- !.