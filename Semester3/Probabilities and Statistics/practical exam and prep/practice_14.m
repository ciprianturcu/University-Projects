clear all
%A study is conducted to compare heat loss in glass pipes, versus steel
%pipes of the same size. Various liquids at identical starting temp are run
%through segments of each type and the heat loss is measured. These data
%results (normality of the two population is assumed):

%steel = [4.6 0.7 4.2 1.9 4.8 6.1 4.7 5.5 5.4]
%glass = [2.5 1.3 2.0 1.8 2.7 3.2 3.0 3.5 3.4]

%a. at the 5% significance level, do the population variances seem to
%differ ?
%b. at the same significance level, does it seem that on average, steel
%pipes lose more heat than glass pipes


steel = [4.6 0.7 4.2 1.9 4.8 6.1 4.7 5.5 5.4];
glass = [2.5 1.3 2.0 1.8 2.7 3.2 3.0 3.5 3.4];

alpha = input('significance level = ');

ns = length(steel);
ng = length(glass);

[H,P,CI,stats] = vartest2(steel,glass,alpha,0);

fprintf('\n\npoint a !\n');

if H
    fprintf("The H0 (null hypothesis) is rejected.\n");
    fprintf("The population variances do not seem to differ.\n");
else
    fprintf("The H0 (null hypothesis) is not rejected.\n");
    fprintf("The population variances do seem to differ.\n");
end

TT1 = finv(alpha/2,ns-1,ng-1);
TT2 = finv(1-alpha/2,ns-1,ng-1);

fprintf('Observed value is %1.4f\n', stats.fstat);
fprintf('P-value is %1.4f\n', P);
fprintf('Rejection region R is (-inf, %3.4f) U (%3.4f, inf)\n', TT1, TT2);

fprintf('\n\npoint b !\n');

%h0 : miu1 = miu2
%h1 : miu1 > miu2 - right tailed test

if H
    [H2,P2,CI2,stats2] = ttest2(steel,glass,alpha,1,'unequal');

    if H2
        fprintf("The H0 (null hypothesis) is rejected.\n");
        fprintf("The steel pipes lose more heat than glass pipes.\n");
    else
        fprintf("The H0 (null hypothesis) is not rejected.\n");
        fprintf("The steel pipes don't lose more heat than glass pipes.\n");
    end

    TT = tinv(1-alpha, stats2.df);

    fprintf('Observed value is %1.4f\n', stats2.tstat);
    fprintf('P-value is %1.4f\n', P2);
    fprintf('Rejection region R is (%3.4f, inf)\n',TT);   
else
    [H2,P2,CI2,stats2] = ttest2(steel,glass,alpha,1,'equal');

    if H2
        fprintf("The H0 (null hypothesis) is rejected.\n");
        fprintf("The steel pipes lose more heat than glass pipes.\n");
    else
        fprintf("The H0 (null hypothesis) is not rejected.\n");
        fprintf("The steel pipes don't lose more heat than glass pipes.\n");
    end

    TT = tinv(1-alpha, stats2.df);

    fprintf('Observed value is %1.4f\n', stats2.tstat);
    fprintf('P-value is %1.4f\n', P2);
    fprintf('Rejection region R is (%3.4f, inf)\n',TT);  
end

