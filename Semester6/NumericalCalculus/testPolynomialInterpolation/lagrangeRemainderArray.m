function Rm = lagrangeRemainderArray(x, nodes, f, m)
    % Numerical estimation of (m+1)-th derivative
    a = -pi;
    b = pi;
    h = 1e-5;  % step size for derivative estimation
    midpoint = (a + b) / 2;
    
    % Forward difference method to estimate the derivative
    derivative_at_midpoint_val = (feval(f, midpoint + (m+1)*h) - feval(f, midpoint)) / ((m+1)*h);
    
    % Compute the factorial of (m+1)
    factorial_m_plus_1 = factorial(m + 1);
    
    % Compute the product term (x - x0)(x - x1)...(x - xm)
    product_term = prod(x - nodes);
    
    % Compute the remainder term Rm
    Rm = product_term / factorial_m_plus_1 * derivative_at_midpoint_val;
end
