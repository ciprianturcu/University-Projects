function Rh = hermiteRemainderArray(x, nodes, f, m)
    % Function for evaluating the (2m+1)-th derivative numerically
    a = min(nodes);
    b = max(nodes);
    h = 1e-5;  % step size for derivative estimation
    
    % Maximum of derivative over the interval
    points = linspace(a, b, 100);
    derivatives = arrayfun(@(p) (feval(f, p + (2*m+1)*h) - feval(f, p)) / ((2*m+1)*h), points);
    max_derivative = max(derivatives);
    
    % Compute the factorial of (2m+1)
    factorial_2m_plus_1 = factorial(2*m + 1);
    
    % Compute the product term (x - x0)^2...(x - xm)^2
    product_term = prod((x - nodes).^2);
    
    % Compute the remainder term Rh
    Rh = product_term / factorial_2m_plus_1 * max_derivative;
end
