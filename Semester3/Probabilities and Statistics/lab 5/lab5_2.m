conflevel = input ('confidence level=');
alpha= 1- conflevel;



X1 = [22.4 21.7 24.5 23.4 21.6 23.3 22.4 21.6 24.8 20.0];
X2 = [17.7 14.8 19.6 19.6 12.1 14.8 15.4 12.6 14.0 12.2];

mean1 = mean(X1);
mean2 = mean(X2);

m = mean1-mean2;

n1 = length(X1);
n2 = length(X2);

var1 = var(X1);
var2 = var(X2);

%a
sp = sqrt(((n1-1)*var1 + (n2-1)*var2)/(n1+n2-2));
t1 = tinv(1 - alpha/2, n1+n2-2);
t2 = tinv(alpha/2, n1+n2-2);
ci1 = m - t1 * sp * sqrt(1/n1+1/n2);
ci2 = m - t2 * sp * sqrt(1/n1+1/n2);
fprintf('confidence level of difference of means (sigma1 == sigma2): (%3.3f, %3.3f)\n', ci1, ci2);

%b

c = (var1/n1) / (var1/n1+ var2/n2);
n = 1/(c^2/(n1-1) + (1-c)^2/(n2-1));
t1 = tinv(1- alpha/2, n);
t2 = tinv(alpha/2, n); 
ci1 = m - t1 * sqrt(var1/n1+ var2/n2);
ci2 = m - t2 * sqrt(var1/n1+ var2/n2);
fprintf('C.I. of difference of means (sigma1 != sigma2): (%3.3f, %3.3f)\n', ci1, ci2);

%c

f1 = finv(1- alpha/2, n1-1, n2-1);
f2 = finv(alpha/2, n1-1, n2-1);
ci1 = 1/f1* var1/var2;
ci2 = 1/f2* var1/var2;
fprintf('C.I. for the ratio of variances: (%3.3f, %3.3f)\n', ci1, ci2);
fprintf('C.I. for the ratio of std. deviations: (%3.3f, %3.3f)\n', sqrt(ci1), sqrt(ci2));








