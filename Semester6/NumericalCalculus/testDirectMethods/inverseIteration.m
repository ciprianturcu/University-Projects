function [lambda, x] = inverseIteration(A, tol, max_iter)
    % Modified inverse iteration that computes the LU decomposition only once.
    n = size(A, 1);
    x = rand(n, 1);
    x = x / norm(x);
    lambda = 0;

    [L, U, P] = lupDecomposition(A - lambda * eye(n));

    for k = 1:max_iter
        % Solve the system using the previously computed LU decomposition
        y = U \ (L \ (P * x));
        x = y / norm(y);
        lambda_old = lambda;
        lambda = x' * A * x;

        if abs(lambda - lambda_old) < tol
            break;
        end
    end
end