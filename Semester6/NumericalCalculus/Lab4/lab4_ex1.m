A = [2 1 -1 -2; 4 4 1 3; -6 -1 10 10; -2 1 8 4];
b = [2; 4; -5; 1];

% Solve using Gaussian Elimination with Partial Pivoting
x_gaussian = gaussianElimination(A,b);
disp('Gaussian Elimination Solution:');
disp(x_gaussian);

% Solve using LUP Factorization
x_lup = lupFactorization(A,b);
disp('LUP Factorization Solution:');
disp(x_lup);

% Solve using QR Factorization (always possible)
x_qr= qrFactorization(A,b);
disp('QR Factorization Solution:');
disp(x_qr);

% Solve using Cholesky Factorization (only if A is symmetric and positive definite)
if issymmetric(A) && all(eig(A) > 0)
    R = chol(A);
    y = backSubstitution(R', b);
    x_cholesky = backSubstitution(R,y);
    disp('Cholesky Factorization Solution:');
    disp(x_cholesky);
else
    disp('Cholesky factorization not applicable');
end
