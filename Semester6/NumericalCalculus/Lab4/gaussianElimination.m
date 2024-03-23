function x = gaussianElimination(A, b)
    n = size(A, 1);
    for i = 1:n
        % Partial pivoting
        [~, maxIndex] = max(abs(A(i:n, i)));
        maxIndex = maxIndex + i - 1;
        A([i, maxIndex], :) = A([maxIndex, i], :);
        b([i, maxIndex]) = b([maxIndex, i]);
        
        % Forward elimination
        for j = i+1:n
            m = A(j, i) / A(i, i);
            A(j, i:n) = A(j, i:n) - m * A(i, i:n);
            b(j) = b(j) - m * b(i);
        end
    end
    
    % Back substitution
    x = backSubstitution(A, b);
end
