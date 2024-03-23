function x = choleskyFactorization(A, b)
    R = chol(A);
    y = forwardSubstitution(R', b); % Solving R' * y = b
    x = backSubstitution(R, y); % Solving R * x = y
end
