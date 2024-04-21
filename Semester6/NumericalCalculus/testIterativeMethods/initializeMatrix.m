function [A, b] = initializeMatrix(n)
 % Indices for the anti-diagonal (put first as per requirement)
    anti_diag_i = 1:n;
    anti_diag_j = n:-1:1;
    anti_diag_s = 0.5 * ones(1, n);

    % Create a sparse matrix for the anti-diagonal
    A = sparse(anti_diag_i, anti_diag_j, anti_diag_s, n, n);

    % Prepare indices and values for the main and off-diagonals
    main_diag_i = 1:n;
    main_diag_j = 1:n;
    main_diag_s = 3 * ones(1, n); % main diagonal values

    off_diag_i = [2:n, 1:n-1];
    off_diag_j = [1:n-1, 2:n];
    off_diag_s = -ones(1, 2*(n-1)); % off-diagonal values
    
    % Update the sparse matrix with main and off-diagonal entries
    A = A + sparse(main_diag_i, main_diag_j, main_diag_s, n, n);
    A = A + sparse(off_diag_i, off_diag_j, off_diag_s, n, n);
    A(n/2,n/2+1) = -1;
    A(n/2+1, n/2) = -1;

    
    
    b = 1.5 * ones(n, 1);
    b([1 end]) = 2.5;
    if mod(n, 2) == 0
        b(n/2:n/2+1) = 1.0;
    end
end
