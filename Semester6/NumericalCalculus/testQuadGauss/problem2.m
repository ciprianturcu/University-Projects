function [I, err] = problem2()
    % Number of nodes
    n = 100;  % Adjust as necessary for precision
    % Get Gauss-Chebyshev #1 nodes and coefficients
    [g_nodes, g_coeff] = Gauss_Cheb1(n);
    
    % Change of interval [-2, 2]
    a = -2;
    b = 2;
    nodes = 0.5 * (b - a) * (g_nodes + 1) + a;
    coeff = 0.5 * (b - a) * g_coeff;
    
    % Define the integrand
    f = @(t) (1 ./ sqrt(4 - t.^2)) .* cos(t);
    
    % Compute the integral using Gauss-Chebyshev #1 quadrature
    I = vquad(nodes, coeff, f);
    
    % Exact integral (for error calculation)
    exact = integral(@(t) (1 ./ sqrt(4 - t.^2)) .* cos(t), -2, 2);
    
    % Compute error
    err = abs(I - exact);
end
