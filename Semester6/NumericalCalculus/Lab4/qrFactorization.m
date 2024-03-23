function x = qrFactorization(A, b)
    [Q, R] = qr(A);
    y = Q' * b;
    x = backSubstitution(R, y);
end
