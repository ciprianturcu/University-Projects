%generate matrix and variables 
n = 1000;
A = 5*eye(n) - diag(ones(n-1,1),1) - diag(ones(n-1,1),-1);
b = ones(n,1);
b(1) = 3;
b(2:3) = 2;
b(end-1) = 2;
b(end) = 3;
w = 1.5;
err = 10^-5;
maxnit=50000;

[x_sor_n1000, nit_sor_n1000] = sorMethod(A, b, w, zeros(n,1), err, maxnit);

disp('For n=1000:');
disp('Solution:');
disp(x_sor_n1000);
disp('Iterations:');
disp(nit_sor_n1000);