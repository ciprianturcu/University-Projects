function [I, err] = problem5(x, y)
    % Number of nodes
    n = 8;
    % Get Gauss-Laguerre nodes and coefficients
    [g_nodes, g_coeff] = Gauss_Laguerre(n, 0);
    
    % Define the integrand
    f = @(t) exp(-x * t) ./ (y + t);
    
    % Compute the integral using Gauss-Laguerre quadrature
    I = vquad(g_nodes, g_coeff, f);
    
    % Exact integral (for error calculation)
    exact = integral(@(t) exp(-x * t) ./ (y + t), 0, Inf);
    
    % Compute error
    err = abs(I - exact);
end
