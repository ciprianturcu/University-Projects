% Problem 6: Solve a linear system with Cholesky decomposition

%Generate a symmetric positive definite matrix A of size n
n = 271;
A = randn(n);  % Generate a random matrix
A = A'*A;      % A'*A is symmetric and positive definite
A = A + n*eye(n);  % Make sure A is positive definite by adding n*I
b = (1:n)';

x = choleskyFactorization(A,b);

%Verify the solution by comparing it with MATLAB's built-in solver
x_verify = A\b;

% Check if the solutions match (within a certain tolerance)
solution_matches = norm(x_verify-x)/norm(x_verify)  < cond(A)*eps;


disp(['Solution check: ', num2str(solution_matches)]);