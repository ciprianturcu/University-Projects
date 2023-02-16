clear all

X = [7 7 4 5 9 9 4 12 8 1 8 7 3 13 2 1 17 7 12 5 6 2 1 13 14 10 2 4 9 11 3 5 12 6 10 7];

M = input('M = ');

alpha = input('alpha = ');

[H,P,CI,STATS] = ttest(X,M,'alpha',alpha,'tail','right');

fprintf("The statistic test is %.3f\n", STATS.tstat);
fprintf("The rejection region is (%.3f,inf)\n",tinv(P,length(X)-1));
fprintf("P  = %.3f\n", P);

if(H)
    fprintf("Mean smaller than %f. We reject H0\n",M);
else
    frpintf("Mean equal to %f. We do not reject H0\n",M);
end

alpha = input('alpha = ');

[H2,P2,CI2,STATS2] = ttest(X,M,'alpha',alpha,'tail','right');

fprintf("The statistic test is %.3f\n", STATS2.tstat);
fprintf("The rejection region is (%.3f,inf)\n",tinv(P2,length(X)-1));
fprintf("P  = %.3f\n", P2);

if(H2)
    fprintf("Mean smaller than %f. We reject H0\n",M);
else
    fprintf("Mean equal to %f. We do not reject H0\n",M);
end
