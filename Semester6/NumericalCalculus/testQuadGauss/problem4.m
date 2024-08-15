function [I, err] = problem4()
    % Number of nodes
    n = 100;  % Adjust as necessary for precision
    % Get Gauss-Hermite nodes and coefficients
    [g_nodes, g_coeff] = Gauss_Hermite(n);
    
    % Define the integrand
    f = @(t) exp(-t.^4) .* cos(t);
    
    % Compute the integral using Gauss-Hermite quadrature
    I = vquad(g_nodes, g_coeff, f);
    
    % Exact integral (for error calculation)
    exact = integral(@(t) exp(-t.^2 - t.^4) .* cos(t), -Inf, Inf);
    
    % Compute error
    err = abs(I - exact);
end
