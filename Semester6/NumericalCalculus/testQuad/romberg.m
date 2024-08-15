% Function for Romberg's method
function I = romberg(f, a, b, tol)
    R = zeros(10, 10);
    h = (b - a);
    R(1, 1) = (f(a) + f(b)) * h / 2;
    for j = 2:10
        h = h / 2;
        sum = 0;
        for i = 1:2^(j-2)
            sum = sum + f(a + (2*i - 1) * h);
        end
        R(j, 1) = R(j-1, 1) / 2 + sum * h;
        for k = 2:j
            R(j, k) = R(j, k-1) + (R(j, k-1) - R(j-1, k-1)) / (4^(k-1) - 1);
        end
        if abs(R(j, j) - R(j-1, j-1)) < tol
            I = R(j, j);
            return;
        end
    end
    I = R(10, 10);
end