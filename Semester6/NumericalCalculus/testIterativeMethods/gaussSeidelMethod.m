function [x, nit] = gaussSeidelMethod(A, b, x0, err, maxnit)
    % Initialize variables
    x = x0; % Initial solution guess
    Q = tril(A); % Lower triangular part of A, essential for Gauss-Seidel
    r = b - A * x; % Initial residual
    nit = maxnit; % Default to max iterations in case convergence criterion is not met

    for i = 1:maxnit
        dx = Q \ r; % Compute update using only the lower triangular part
        x = x + dx; % Update the solution
        r = b - A * x; % Update residual
        
        % Convergence check using both residual and dx
        if all(abs(r) < err) && all(abs(dx) < err)
            nit = i;
            return;
        end
    end
end
