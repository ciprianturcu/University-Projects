function [I, nfev] = booleRule(f, a, b)
    % Boole's rule for integration
    x0 = a;
    x1 = (3*a + b) / 4;
    x2 = (a + b) / 2;
    x3 = (a + 3*b) / 4;
    x4 = b;
    I = (b - a) / 90 * (7*f(x0) + 32*f(x1) + 12*f(x2) + 32*f(x3) + 7*f(x4));
    nfev = 5;
end
