clear all
%To reach maximum efficiency in performing an assembling operation in a
%manufacturing plant, new employees are required to take a 1-month training
%course. A new method was suggested, and the manager wants to compare the
%new method with the standard one, by looking at the lengths of time
%required for employees to assemble a certain device. They are given
%below (and assumed approximately normally distributed)

%standard = [46 37 39 48 47 44 35 31 44 37]
%new = [35 33 31 35 34 30 27 32 31 31]

%a. at the 5% significance level, do the population variances seem to
%differ ?
%b. find a 95% confidence interval for the difference of the average
%assembling times.

standard = [46 37 39 48 47 44 35 31 44 37];
new = [35 33 31 35 34 30 27 32 31 31];

ns = length(standard);
nn = length(new);

alpha = input('significance level = ');

%tail - values : -1 for left tailed
%                 0 two tailed (default)
%                 1 for right tailed

[H,P,CI,stats] = vartest2(standard,new,alpha,0);

fprintf('\n\npoint a !\n');
if H
    fprintf("The H0 (null hypothesis) is rejected.\n");
    fprintf("The population variances do not seem to differ.\n");
else
    fprintf("The H0 (null hypothesis) is not rejected.\n");
    fprintf("The population variances do seem to differ.\n");
end

TT1 = finv(alpha/2, ns-1, nn-1);
TT2 = finv(1-alpha/2,ns-1, nn-1);

fprintf('Observed value is %1.4f\n', stats.fstat);
fprintf('P-value is %1.4f\n', P);
fprintf('Rejection region R is (-inf, %3.4f) U (%3.4f, inf)\n', TT1, TT2);

fprintf('\n\npoint b !\n');

ms = mean(standard);
mn = mean(new);
vs = var(standard);
vn = var(new);

if H
    %sigma unequal and unknown
    c = (vs/ns)/(vs/ns+vn/nn);
    n = c^2/(ns-1) + (1-c)^2/(nn-1);
    n=1/n;
    t = tinv(1-alpha/2,n);
    left = ms - mn - t * sqrt(vs/ns+vn/nn);
    right = ms - mn + t * sqrt(vs/ns+vn/nn);
else
    %sigma equal and unknown
    n = ns + nn - 2;
    t = tinv(1-alpha/2,n);
    sp = sqrt(((ns-1)*vs + (nn-1)*vn)/n);
    left = ms - mn - t * sp * sqrt(1/ns+1/nn);
    right = ms - mn + t * sp * sqrt(1/ns+1/nn);
end

fprintf("The 95 confidence interval is : (%.4f, %.4f)\n", left,right);

