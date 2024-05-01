function Rh = hermiteRemainder(x, nodes, f, m)
    % x: Point at which to evaluate the remainder
    % nodes: Vector of interpolation nodes
    % f: The function handle of the original function
    % m: The number of nodes minus one (degree of polynomial)

    syms s
    % Define the function symbolically using the function handle
    f_sym = f(s);
    
    % Compute the (2m+1)-th derivative of f
    f_2m_plus_1_derivative = diff(f_sym, 2*m + 1);
    
    % Convert the symbolic (2m+1)-th derivative to a function handle
    f_2m_plus_1_derivative_fn = matlabFunction(f_2m_plus_1_derivative, 'Vars', s);
    
    % Find a point to evaluate the maximum of the (2m+1)-th derivative.
    % Using the midpoint for simplicity. For better accuracy, find the max over [a, b]
    a = min(nodes);
    b = max(nodes);
    midpoint = (a + b) / 2;
    
    % Evaluate the (2m+1)-th derivative at the midpoint
    max_derivative = f_2m_plus_1_derivative_fn(midpoint);
    
    % Compute the factorial of (2m+1)
    factorial_2m_plus_1 = factorial(2*m + 1);
    
    % Compute the product term (x - x0)^2...(x - xm)^2
    product_term = prod((x - nodes).^2);
    
    % Compute the remainder term Rh
    Rh = double(product_term / factorial_2m_plus_1 * max_derivative);
end