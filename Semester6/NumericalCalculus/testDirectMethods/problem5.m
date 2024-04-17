% Define the tolerance and maximum number of iterations
tol = 1e-10;
max_iter = 1000;

% Define four symmetric matrices for testing
A1 = [4, 1, 1; 1, 3, -1; 1, -1, 2];
A2 = [40, -20, 0; -20, 40, -20; 0, -20, 40];
A3 = rand(5, 5); A3 = (A3 + A3') / 2; % 5x5 random symmetric matrix
A4 = hilb(4); % 4x4 Hilbert matrix

% Apply the modified inverse iteration to each matrix
[lambda1, x1] = inverseIteration(A1, tol, max_iter);
[lambda2, x2] = inverseIteration(A2, tol, max_iter);
[lambda3, x3] = inverseIteration(A3, tol, max_iter);
[lambda4, x4] = inverseIteration(A4, tol, max_iter);

% Display the results
fprintf('Eigenvalue for A1: %f\n', lambda1);
fprintf('Eigenvector for A1:\n');
disp(x1);

fprintf('Eigenvalue for A2: %f\n', lambda2);
fprintf('Eigenvector for A2:\n');
disp(x2);

fprintf('Eigenvalue for A3: %f\n', lambda3);
fprintf('Eigenvector for A3:\n');
disp(x3);

fprintf('Eigenvalue for A4: %f\n', lambda4);
fprintf('Eigenvector for A4:\n');
disp(x4);

% Verify the results using MATLAB's eig function
eigenvalues1 = eig(A1);
eigenvalues2 = eig(A2);
eigenvalues3 = eig(A3);
eigenvalues4 = eig(A4);

fprintf('Smallest eigenvalue by MATLAB eig for A1: %f\n', min(abs(eigenvalues1)));
fprintf('Smallest eigenvalue by MATLAB eig for A2: %f\n', min(abs(eigenvalues2)));
fprintf('Smallest eigenvalue by MATLAB eig for A3: %f\n', min(abs(eigenvalues3)));
fprintf('Smallest eigenvalue by MATLAB eig for A4: %f\n', min(abs(eigenvalues4)));
