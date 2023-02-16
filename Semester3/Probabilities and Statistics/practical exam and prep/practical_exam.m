clear all

steel = [4.6 0.7 4.2 1.9 4.8 6.1 4.7 5.5 5.4];
glass = [2.5 1.3 2.0 1.8 2.7 3.2 3.0 3.5 3.4];

ns = length(steel);
ng = length(glass);

fprintf('\n\npoint a !\n');

alpha = input('significance level = ');

fprintf("We are doing a two tailed vartest for 2 populations.\n")
fprintf("The null hypothesis H0: population variances are equal.\n")
fprintf("The alternative hypothesis H1 : population variances differ.\n")
[H,P,CI,stats] = vartest2(steel,glass,alpha,0);

if H
    fprintf("The H0 (null hypothesis) is rejected.\n");
    fprintf("The population variances seem to differ.\n");
else
    fprintf("The H0 (null hypothesis) is not rejected.\n");
    fprintf("The population variances do not seem to differ.\n");
end

TT1 = finv(alpha/2,ns-1,ng-1);
TT2 = finv(1-alpha/2,ns-1,ng-1);

fprintf('Observed value is %1.4f\n', stats.fstat);
fprintf('P-value is %1.4f\n', P);
fprintf('Rejection region R is (-inf, %3.4f) U (%3.4f, inf)\n', TT1, TT2);

fprintf('\n\npoint b !\n');

fprintf("The null hypothesis H0: steel pipes do not lose more heat than glass pipes.\n");
fprintf("The alternative hypothesis H1 : steel pipes do lose more heat than glass pipes.\n");
fprintf("H0: miu1 = miu2 ; H1:  miu1 > miu2 so we do a right tailed ttest on 2 populations.\n");
fprintf("The test has two possibilities due to point a, equal or unequal should be used acording to the value from point a.\n");

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

    %vs = var(steel);
    %vg = var(glass);
    %ms = mean(steel);
    %mg = mean(glass);

    %c = (vs/ns)/((vs/ns)+(vg/ng));
    %n = c^2/(ns-1) + (1-c)^2/(ng-1);
    %n=1/n;

    TT = tinv(1-alpha, stats2.df);

    fprintf('Observed value is %1.4f\n', stats2.tstat);
    fprintf('P-value is %1.4f\n', P2);
    fprintf('Rejection region R is (%3.4f, inf)\n',TT);
end






