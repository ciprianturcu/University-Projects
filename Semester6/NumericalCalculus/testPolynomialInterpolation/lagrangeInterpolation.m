function Lx = lagrangeInterpolation(x_nodes, y_nodes, x_eval)
    % lagrangeInterpolation - Computes the Lagrange interpolation polynomial
    % at given evaluation points.
    %
    % Syntax: Lx = lagrangeInterpolation(x_nodes, y_nodes, x_eval)
    %
    % Inputs:
    %   x_nodes - Vector of node x-coordinates
    %   y_nodes - Vector of node y-values
    %   x_eval - Vector of points where interpolation is evaluated
    %
    % Outputs:
    %   Lx - Interpolated values at x_eval points
    
    % Initialize the output array for evaluated points
    [mu, nu] = size(x_eval);
    Lx = zeros(mu, nu);
    
    % Get the number of nodes
    n = length(x_nodes);
    
    % Calculate the Lagrange interpolation polynomial at each x_eval point
    for i = 1:n
        % Initialize product term z for the i-th Lagrange basis polynomial
        z = ones(mu, nu);
        
        % Compute the i-th Lagrange basis polynomial li
        for j = [1:i-1, i+1:n]
            z = z .* (x_eval - x_nodes(j)) / (x_nodes(i) - x_nodes(j));
        end
        
        % Add the i-th term to the Lagrange polynomial sum
        Lx = Lx + z * y_nodes(i);
    end
end