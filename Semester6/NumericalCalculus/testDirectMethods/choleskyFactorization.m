function x = choleskyFactorization(A, b)
    R = choleskyDecomposition(A);
    y = forwardSubstitution(R', b);
    x = backSubstitution(R, y);
end
