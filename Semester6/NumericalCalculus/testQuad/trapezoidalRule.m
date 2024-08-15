function [I, nfev] = trapezoidalRule(f, a, b, m)
    % Composite trapezoidal rule with m subintervals
    x = linspace(a, b, m + 1);
    y = f(x);
    h = (b - a) / m;
    I = h * (sum(y) - 0.5 * (y(1) + y(end)));
    nfev = length(x);
end
