function [I, err] = problem3()
    % Number of nodes
    n = 100;  % Adjust as necessary for precision
    % Get Gauss-Chebyshev #2 nodes and coefficients
    [g_nodes, g_coeff] = Gauss_Cheb2(n);
    
    % Change of interval [1, 2]
    a = 1;
    b = 2;
    nodes = 0.5 * (b - a) * (g_nodes + 1) + a;
    coeff = 0.5 * (b - a) * g_coeff;
    
    % Define the integrand
    f = @(t) sqrt(3*t - t.^2 - 2) .* sin(t);
    
    % Compute the integral using Gauss-Chebyshev #2 quadrature
    I = vquad(nodes, coeff, f);
    
    % Exact integral (for error calculation)
    exact = integral(@(t) sqrt(3*t - t.^2 - 2) .* sin(t), 1, 2);
    
    % Compute error
    err = abs(I - exact);
end
