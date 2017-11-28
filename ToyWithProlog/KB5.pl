bitvec(0,[]):- !.
bitvec(Number, [0|LastList]):- NextNum is Number - 1, bitvec(NextNum, LastList).
bitvec(Number, [1|LastList]):- NextNum is Number - 1, bitvec(NextNum, LastList).

calculate(OneBits,[]):- OneBits =:= 0.

calculate(OneBits, [1|List]):- OneBits > 0, !, Less is OneBits - 1, calculate(Less, List).
calculate(OneBits, [0|List]):- OneBits >= 0, calculate(OneBits, List).

code(Total, NumofOnes, List):- bitvec(Total, List), calculate(NumofOnes, List).