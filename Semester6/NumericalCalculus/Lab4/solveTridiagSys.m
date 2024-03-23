function x = solveTridiagSys(n)
    % Generate the tridiagonal matrix A of size n
    A = diag(5*ones(n,1)) + diag(-1*ones(n-1,1),1) + diag(-1*ones(n-1,1),-1);
    A(end,end) = 5;  % Last value in the diagonal based on the given system

    % Generate vector b with the given values
    b = 3*ones(n,1);
    b(1) = 4; b(end) = 4;  % First and last values based on the given system

    % Solve the system using Gaussian Elimination
    x_gaussian = gaussianElimination(A,b);

    % Solve using LUP Factorization
    x_lup = lupFactorization(A,b);

    % Solve using QR Factorization (always possible)
    x_qr = qrFactorization(A,b);

    % Display results
    disp('Gaussian Elimination Solution:');
    disp(x_gaussian);
    disp('LUP Factorization Solution:');
    disp(x_lup);
    disp('QR Factorization Solution:');
    disp(x_qr);
    
    % Return the solutions
    x = struct('Gaussian', x_gaussian, 'LUP', x_lup, 'QR', x_qr);
end