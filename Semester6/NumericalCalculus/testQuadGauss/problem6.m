function [I, err] = problem6()
    % Number of nodes
    n = 100;  % Adjust as necessary for precision
    % Parameters alpha and beta
    alpha = 2;
    beta = 2;
    % Get Gauss-Jacobi nodes and coefficients
    [g_nodes, g_coeff] = Gauss_Jacobi(n, alpha, beta);
    
    % Change of interval [0, pi]
    a = 0;
    b = pi;
    nodes = 0.5 * (b - a) * (g_nodes + 1) + a;
    coeff = 0.5 * (b - a) * g_coeff;
    
    % Define the integrand
    f = @(t) cos(t) ./ (2 + cos(t));
    g = @(t) f(t) .* (cos(t / 2)).^alpha .* (sin(t / 2)).^beta;
    
    % Compute the integral using Gauss-Jacobi quadrature
    I = vquad(nodes, coeff, g);
    
    % Exact integral (for error calculation)
    exact = integral(@(t) f(t) .* (cos(t / 2)).^alpha .* (sin(t / 2)).^beta, 0, pi);
    
    % Compute error
    err = abs(I - exact);
end
