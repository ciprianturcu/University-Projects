clear all

%test for 2 populations
sample_a = [1021 980 1017 988 1005 998 1014 985 995 1004 1030 1015 995 1023];
sample_b = [1070 970 993 1013 1006 1002 1014 997 1002 1010 975];

na = length(sample_a);
nb = length(sample_b);


alpha = input('significance level = ');

%tail - values : -1 for left tailed
%                 0 two tailed (default)
%                 1 for right tailed

[H,P,CI,stats] = vartest2(sample_a,sample_b,alpha,0);

%if h is rejected 
if H
    fprintf("We reject H0, pop var not equal\n");
else
    fprintf("We do not reject H0, pop var are equal\n");
end

tt1 = finv(alpha/2, na-1, nb-1);
tt2 = finv(1-alpha/2,na-1,nb-1);

fprintf('Observed value is %1.4f\n', stats.fstat);
fprintf('P-value is %1.4f\n', P);
fprintf('Rejection region R is (-inf, %3.4f) U (%3.4f, inf)\n', tt1, tt2);

fprintf('\n\npoint b !\n');

%b
%h0 : miu1 = miu2
%h1 : miu1 > miu2 right tail test

[H2,P2,CI2,stats2] = ttest2(sample_a,sample_b, alpha, 1, 'equal');
%vartype - equal (bcs in point a. we got same values for population
%variances. If they were unequal, we would have used 'unequal' parameter.

if H2
    fprintf("We reject H0, pop var are not equal\n");
else
    fprintf("We do not reject H0, pop var are equal\n");
end

fprintf('P-value of the test statistic is %1.4f.\n', P2)
fprintf('Observed value of the test statistic is %1.4f.\n', stats2.tstat)

q1 = tinv(1-alpha, stats2.df);

fprintf('Rejection region R is (%3.4f, +inf)\n', q1)
fprintf('\n\n\n\n');

