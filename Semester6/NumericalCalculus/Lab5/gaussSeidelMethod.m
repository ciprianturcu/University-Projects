function [x, nit] = gaussSeidelMethod(A, b, x0, err, maxnit)
    % Decompose A into its diagonal, lower, and upper parts
    D = diag(diag(A));
    L = tril(A, -1);
    U = triu(A, 1);

    % Calculate M and N for Gauss-Seidel
    M = D + L; % This is the part of A that Gauss-Seidel uses for immediate updates
    N = -U; % The remainder of A not immediately updated
    
    % Precompute T and c
    M_inv = inv(M);
    T = M_inv * N;
    c = M_inv * b;
    
    % Calculate the convergence criterion based on the norm of T
    alpha = norm(T, inf);
    
    % Initialize the solution with the initial guess
    x = x0;
    
    for k = 1:maxnit
        x_old = x; % Store the current x to calculate the difference later
        x = T * x_old + c; % Update step
        
        % Check for convergence
        if norm(x - x_old, inf) < err * (1 - alpha) / alpha
            nit = k;
            return; % Successfully converged
        end
    end
    
    % If function reaches here, it hit the max number of iterations
    nit = maxnit;
end
