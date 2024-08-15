function [I_sin, I_cos, err_sin, err_cos] = problem1()
    % Number of nodes
    n = 10;
    % Get Gauss-Legendre nodes and coefficients
    [g_nodes, g_coeff] = Gauss_Legendre(n);
    
    % Change of interval [0, pi]
    a = 0;
    b = pi;
    nodes = 0.5 * (b - a) * (g_nodes + 1) + a;
    coeff = 0.5 * (b - a) * g_coeff;
    
    % Define the integrands
    f_sin = @(t) sin(t.^2);
    f_cos = @(t) cos(t.^2);
    
    % Compute the integrals using Gauss-Legendre quadrature
    I_sin = vquad(nodes, coeff, f_sin);
    I_cos = vquad(nodes, coeff, f_cos);
    
    % Exact integrals (for error calculation)
    exact_sin = integral(@(t) sin(t.^2), 0, pi);
    exact_cos = integral(@(t) cos(t.^2), 0, pi);
    
    % Compute errors
    err_sin = abs(I_sin - exact_sin);
    err_cos = abs(I_cos - exact_cos);
end
