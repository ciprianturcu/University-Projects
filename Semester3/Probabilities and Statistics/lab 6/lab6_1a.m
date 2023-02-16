clear all

X = [7 7 4 5 9 9 4 12 8 1 8 7 3 13 2 1 17 7 12 5 6 2 1 13 14 10 2 4 9 11 3 5 12 6 10 7];

sigma = input('sigma = ');
alpha = input('alpha = ');

M = input('M = ');

[H, P, ci, z]=ztest(X, M ,sigma, 'alpha',alpha,'tail', 'left');

fprintf("The statistic test is %.3f\n", z);
fprintf("The rejection region is (-inf,%.3f)\n",norminv(P));
fprintf("P  = %.3f\n", P);

if(H)
    disp('Mean smaller than 9. We reject H0');
else
    disp('Mean equal to 9. We do not reject H0');
end

alpha = input('alpha = ');

[H2, P2, ci2, z2] = ztest(X, M,sigma, 'alpha', alpha, 'tail', 'left');

fprintf("The statistic test is %.3f\n", z2);
fprintf("The rejection region is (-inf,%.3f)\n",norminv(P2));
fprintf("P  = %.3f\n", P2);

if(H2)
    disp('Mean smaller than 9. We reject H0');
else
    disp('Mean equal to 9. We do not reject H0');
end