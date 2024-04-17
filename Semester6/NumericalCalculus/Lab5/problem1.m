%Problem 1

%Generate system and variables
n=1000;
[A,b] = generateAandb(n);
x0=zeros(size(b));
maxint = 100;
err = 10^-5;

%Jacobi Method
[x_jacobi,nit_jacobi] = jacobiMethod(A,b,x0,err,maxint);


disp('Jacobi Method:');
disp('Solution:');
disp(x_jacobi);
disp('Iterations:');
disp(nit_jacobi);

%Gauss-Seidel Method
[x_gauss, nit_gauss] = gaussSeidelMethod(A,b,x0,err,maxint);
disp('Gauss-Seidel Method:');
disp('Solution:');
disp(x_gauss);
disp('Iterations:');
disp(nit_gauss);