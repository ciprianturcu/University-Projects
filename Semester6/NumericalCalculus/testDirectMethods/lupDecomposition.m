function [L, U, P] = lupDecomposition(A)
    % LUP decomposition with partial pivoting.
    n = size(A, 1);
    L = eye(n);
    U = A;
    P = eye(n);
    for i = 1:n
        [~, pivot] = max(abs(U(i:n, i)));
        pivot = pivot + i - 1;
        if i ~= pivot
            U([i pivot], :) = U([pivot i], :);
            P([i pivot], :) = P([pivot i], :);
            if i >= 2
                L([i pivot], 1:i-1) = L([pivot i], 1:i-1);
            end
        end
        for j = i+1:n
            L(j, i) = U(j, i) / U(i, i);
            U(j, i:n) = U(j, i:n) - L(j, i)*U(i, i:n);
        end
    end
end