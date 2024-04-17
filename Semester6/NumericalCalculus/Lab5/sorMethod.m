function [x, nit] = sorMethod(A, b, w, x0, err, maxnit)
    % Decompose A into its diagonal (D), upper (U), and lower (L) parts
    D = diag(diag(A));
    U = triu(A, 1);
    L = tril(A, -1);

    % Calculate M and N 
    M = D / w + L; 
    N = ((1 - w) / w) * D - U; 
    
    % Precompute T and c
    M_inv = inv(M); 
    T = M_inv * N;
    c = M_inv * b;
    
    % Calculate the convergence criterion based on the norm of T
    alpha = norm(T, inf);
    
    % Initialize the solution with the initial guess
    x = x0;
    
    for k = 1:maxnit
        x_old = x; % Store the current x for difference calculation
        x = T * x_old + c; % Perform the update
        
        % Check for convergence 
        if norm(x - x_old, inf) < err * (1 - alpha) / alpha
            nit = k;
            return; % Successful convergence
        end
    end
    
    % Reached maximum iterations without meeting convergence criterion
    nit = maxnit;
end
