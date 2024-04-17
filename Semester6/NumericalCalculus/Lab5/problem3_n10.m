%generate matrix and variables 
n = 10;
A = 5*eye(n) - diag(ones(n-1,1),1) - diag(ones(n-1,1),-1);
b = ones(n,1);
b(1) = 3;
b(2:3) = 2;
b(end-1) = 2;
b(end) = 3;
w = 1.5;
err = 10^-5;
maxnit=50000;

[x_sor_n10, nit_sor_n10] = sorMethod(A, b, w, zeros(n,1), err, maxnit);

disp('For n=10:');
disp('Solution:');
disp(x_sor_n10);
disp('Iterations:');
disp(nit_sor_n10);