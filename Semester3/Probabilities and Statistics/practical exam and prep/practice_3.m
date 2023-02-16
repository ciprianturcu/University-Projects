clear all
%Nickel powders are used in coatings used to shield electomagnetic equipment. 
%A random sample is selected, and the sizes of nickel particles in each coating are recorded (they are assumed to be approximately normally distributed):
%3.26 1.89 2.42 2.03 3.07 2.95 1.39 3.06 2.46 3.35 1.56 1.79  1.76 3.82 2.42 2.96

%a.Find a 95% confidence interval for the average size nickel particles.
%b.At the 1% significance level, on average, do these nickel particles seem to be smaller than 3 ?

sample = [3.26 1.89 2.42 2.03 3.07 2.95 1.39 3.06 2.46 3.35 1.56 1.79  1.76 3.82 2.42 2.96];

n = length(sample);
alpha = 0.05;
TT = tinv(1-alpha/2,n-1);
m = mean(sample);
s = std(sample);

left = m - (s/sqrt(n))*TT;
right = m + (s/sqrt(n))*TT;

fprintf('\n\npoint a !\n');
fprintf("The 95 confidence interval is (%.4f,%.4f)\n",left,right);

fprintf('\n\npoint b !\n');
m=3;
alpha = input('significance level = ');

[H,P,CI,stats] = ttest(sample,m,alpha,-1);

if H
    fprintf("The H0 (null hypothesis) is rejected.\n");
    fprintf("On average the nickel particles are smaller than 3.\n");
else
    fprintf("The H0 (null hypothesis) is not rejected.\n");
    fprintf("On average the nickel particles are not smaller than 3.\n");
end

TT = tinv(alpha,n-1);

fprintf('Observed value is %1.4f\n', stats.tstat);
fprintf('P-value is %1.4f\n', P);
fprintf('Rejection region R is (-inf, %3.4f)\n',TT);

