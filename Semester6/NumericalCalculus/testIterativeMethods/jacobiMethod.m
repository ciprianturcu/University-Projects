function [x, nit] = jacobiMethod(A, b, x0, err, maxnit)
    n = length(b);
    D = spdiags(diag(A), 0, n, n); % Keep D sparse
    R = A - D; % R remains sparse
    
    x = x0;
    for k = 1:maxnit
        x_old = x; % Store the current x to calculate the difference later
        % Update step optimized for sparse matrices
        x = (b - R * x_old) ./ diag(D); 
        
        % Check for convergence
        if norm(x - x_old, inf) < err
            nit = k;
            return;
        end
    end
    
    % If the function reaches here, it has hit the maximum number of iterations
    nit = maxnit;
end
