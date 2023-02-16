clear all

X1 = [22.4 21.7 24.5 23.4 21.6 23.3 22.4 21.6 24.8 20.0];
X2 = [17.7 14.8 19.6 19.6 12.1 14.8 15.4 12.6 14.0 12.2];

alpha = input('alpha = ');

[H,P,CI,STATS] = vartest2(X1,X2,'alpha',alpha,'tail','both');

fprintf("The statistic test is %.3f\n", STATS.fstat);
fprintf("The rejection region is (-inf,%.3f)U(%.3f,inf)\n",finv(alpha/2, length(X1)-1, length(X2)-1), finv(1-alpha/2, length(X1)-1, length(X2)-1));
fprintf("P  = %.3f\n", P);

if(H)
    fprintf("We reject H0\n");
else
    fprintf("We do not reject H0\n");
end
