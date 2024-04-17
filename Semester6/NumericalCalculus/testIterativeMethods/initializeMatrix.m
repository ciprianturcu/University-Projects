function [A, b] = initializeMatrix(n)
    e = ones(n, 1);
    A = spdiags([-e/2 3*e -e/2], -1:1, n, n);
    A = spdiags(e/2, n-1, A);
    A = spdiags(e/2, 1-n, A);
    
    b = 1.5 * ones(n, 1);
    b([1 end]) = 2.5;
    if mod(n, 2) == 0
        b(n/2:n/2+1) = 1.0;
    end
end
