sin_zero(X,Y):- Temp is sin(X), Temp < 0.0001, Temp > -0.0001, !, Y is X.

sin_zero(X,Y):- Diff is sin(X)/cos(X), Temp is X - Diff, sin_zero(Temp, Y).