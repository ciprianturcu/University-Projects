function Rm = lagrangeRemainder(x, nodes, m)
    % x: Point at which to evaluate the remainder
    % nodes: Vector of interpolation nodes
    % m: The number of nodes minus one

    syms s
    % Define the function symbolically
    f_sym = s * cos(s^2);
    
    % Compute the (m+1)-th derivative of f
    f_m_plus_1_derivative = diff(f_sym, m + 1);
    
    % Substitute the midpoint into the derivative to find its value there
    a = -pi;
    b = pi;
    midpoint = (a + b) / 2;
    
    % Convert the symbolic derivative to a numerical value at the midpoint
    derivative_at_midpoint = subs(f_m_plus_1_derivative, s, midpoint);
    derivative_at_midpoint_val = double(derivative_at_midpoint);
    
    % Compute the factorial of (m+1)
    factorial_m_plus_1 = factorial(m + 1);
    
    % Compute the product term (x - x0)(x - x1)...(x - xm)
    product_term = prod(x - nodes);
    
    % Compute the remainder term Rm
    Rm = product_term / factorial_m_plus_1 * derivative_at_midpoint_val;
end