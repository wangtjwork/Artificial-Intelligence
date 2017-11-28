remdups([Head],[Head]).
remdups([Head|Remain],[Head|Resu]):- \+member(Head,Remain),remdups(Remain, Resu).
remdups([Head|Remain],Resu):- member(Head,Remain),remdups(Remain, Resu).