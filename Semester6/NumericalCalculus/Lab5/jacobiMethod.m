function [x, nit] = jacobiMethod(A, b, x0, err, maxnit)
    % Decompose A into its diagonal and off-diagonal parts
    D = diag(diag(A));
    R = A - D;
    
    % Precompute the inverse of the diagonal matrix D
    D_inv = diag(1 ./ diag(D));
    
    % Precompute matrix T and vector c for efficiency
    T = D_inv * R;
    c = D_inv * b;
    
    % Calculate the convergence criterion based on the norm of T
    alpha = norm(T, inf);
    
    % Initialize the solution with the initial guess
    x = x0;
    
    for k = 1:maxnit
        x_old = x; % Store the current x to calculate the difference later
        x = -T * x_old + c;
        
        % Check for convergence
        if norm(x - x_old, inf) < err * (1 - alpha) / alpha
            nit = k;
            return;
        end
    end
    
    % If the function reaches here, it has hit the maximum number of iterations
    nit = maxnit;
end
