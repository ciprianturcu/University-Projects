function [x, nit] = sorMethod(A, b, w, x0, tol, maxIter)
    % Ensure A is in sparse format for efficiency
    if ~issparse(A)
        A = sparse(A);
    end
    
    n = size(A, 1); % Size of the matrix
    x = x0; % Initial solution vector
    D = spdiags(spdiags(A,0), 0, n, n); % Extract diagonal elements of A
    L = tril(A, -1); % Lower triangular part of A
    U = triu(A, 1); % Upper triangular part of A
    invD = spdiags(1./spdiags(A, 0), 0, n, n); % Inverse of D
    
    for nit = 1:maxIter
        % SOR iteration using sparse matrix operations
        x_old = x;
        % x = (D + wL)^-1 * [w*b - (wU + (w-1)D)x_old]
        % which simplifies to:
        x = invD * (w * (b - (L + U) * x_old) + (1 - w) * D * x_old);
        
        % Checking convergence
        if norm(x - x_old, inf) < tol
            return;
        end
    end
    
    fprintf('Maximum iteration limit reached without convergence.\n');
end
