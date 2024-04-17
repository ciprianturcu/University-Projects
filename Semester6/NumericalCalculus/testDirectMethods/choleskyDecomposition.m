function R = choleskyDecomposition(A)
    %CHOLESKY - Cholesky factorization optimized based on user input.
    %   R = Cholesky(A) computes the Cholesky decomposition of a symmetric
    %   positive-definite matrix A, such that A = R'*R. The result R is an
    %   upper triangular matrix.

    [m, n] = size(A);
    if m ~= n
        error('Matrix A must be square.');
    end
    
    % Ensure A is symmetric
    if ~isequal(A, A')
        error('Matrix A must be symmetric.');
    end

    for k = 1:m
        % Check if the matrix is Hermitian positive-definite (HPD)
        if A(k, k) <= 0
            error('Matrix is not HPD.');
        end
        for j = k + 1:m
            A(j, j:m) = A(j, j:m) - A(k, j:m) * (A(k, j) / A(k, k));
        end
        A(k, k:m) = A(k, k:m) / sqrt(A(k, k));
    end
    
    R = triu(A); % Extract the upper triangular matrix
end
