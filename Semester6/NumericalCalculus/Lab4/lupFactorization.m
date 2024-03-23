function x = lupFactorization(A, b)
    [L, U, P] = lu(A);
    y = forwardSubstitution(L, P * b);
    x = backSubstitution(U, y);
end
