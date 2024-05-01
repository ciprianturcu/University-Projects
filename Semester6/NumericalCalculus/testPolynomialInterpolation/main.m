% Define the function f(x) = x*sin(x^2) on the interval [-pi, pi]
f = @(x) x .* sin(x.^2);
df = @(x) sin(x.^2) + 2 * x.^2 .* cos(x.^2); % Derivative of f
a = -pi;
b = pi;
t = pi/5;

% Define the number of Chebyshev nodes
m = 9;

% Compute Chebyshev nodes
k = 0:m;
x_cheb = cos((2.*k + 1) * pi / (2 * (m + 1))) * (b - a) / 2 + (b + a) / 2;

% Compute the values of the function at the Chebyshev nodes
y_cheb = f(x_cheb);
y_cheb_prime = df(x_cheb);

%Lagrange Polynomial
p_lagrange = @(x) lagrangeInterpolation(x_cheb, y_cheb, x);

% Compute Hermite divided differences
[z_cheb, td_hermite] = divdiffdn(x_cheb, y_cheb, y_cheb_prime);

% Hermite Interpolation Polynomial using Newton form
p_hermite = @(x) Newtonpol(td_hermite, z_cheb, x);

% Plot the function and the interpolation polynomials
x_values = linspace(a, b, 1000);
figure;
plot(x_values, f(x_values), 'k', 'LineWidth', 2);
hold on;
plot(x_values, p_lagrange(x_values), 'r--', 'LineWidth', 2);
plot(x_values, p_hermite(x_values), 'b-.', 'LineWidth', 2);
legend('Function f(x)', 'Lagrange Polynomial','Hermite Polynomial');
title('Function and Interpolation Polynomials');
xlabel('x');
ylabel('y');
grid on;
hold off;

% Approximate f(t) using both types of interpolation polynomial
f_lagrange_t = p_lagrange(t);
f_hermite_t = p_hermite(t);

% Display the approximations
fprintf('Approximation at t using Lagrange: %f\n', f_lagrange_t);
fprintf('Approximation at t using Hermite: %f\n', f_hermite_t);

% Evaluate the actual function at a range of values
x_values = linspace(a, b, 1000);
f_values = f(x_values);

% Interpolations over the range
lagrange_values = p_lagrange(x_values);
hermite_values = p_hermite(x_values);

% Calculate practical errors over the range
practical_error_lagrange = abs(f_values - lagrange_values);
practical_error_hermite = abs(f_values - hermite_values);

% Calculate theoretical errors using remainder functions over the range
lagrange_theoretical_errors = arrayfun(@(x) lagrangeRemainderArray(x, x_cheb, f, m), x_values);
hermite_theoretical_errors = arrayfun(@(x) hermiteRemainderArray(x, x_cheb, f, m), x_values);

% Plotting results
figure;
subplot(2, 1, 1); % Practical Errors Plot
plot(x_values, practical_error_lagrange, 'r-', x_values, practical_error_hermite, 'b--');
title('Practical Error Comparison');
xlabel('x');
ylabel('Error');
legend('Lagrange', 'Hermite');
grid on;

subplot(2, 1, 2); % Theoretical Errors Plot
plot(x_values, lagrange_theoretical_errors, 'r-', x_values, hermite_theoretical_errors, 'b--');
title('Theoretical Error Comparison');
xlabel('x');
ylabel('Error');
legend('Lagrange', 'Hermite');
grid on;

% Report maximum errors and errors at specific point t
fprintf('Practical error at t using Lagrange: %f\n', abs(f(t) - p_lagrange(t)));
fprintf('Practical error at t using Hermite: %f\n', abs(f(t) - p_hermite(t)));

