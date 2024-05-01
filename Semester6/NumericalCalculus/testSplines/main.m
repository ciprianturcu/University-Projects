% Define the function
f = @(x) x + sin(x.^2);

% Define the domain and number of nodes
n = 10;
x_range = linspace(-pi, pi, 1000);  % Range for plotting the function and splines

% Nodes: Equispaced and Chebyshev Type 2
x_equispaced = linspace(-pi, pi, n);
x_chebyshev2 = -cos(pi*(2*(1:n)-1)/(2*n));  % Chebyshev nodes of the second kind

% Function values at the nodes
y_equispaced = f(x_equispaced);
y_chebyshev2 = f(x_chebyshev2);

% Derivative function, required for complete spline (if using clamped conditions)
df = @(x) 1 + 2*x.*cos(x.^2);

% Derivatives at the boundaries (required for complete splines if clamped)
dy1_equispaced = df(x_equispaced(1));
dy2_equispaced = df(x_equispaced(end));
dy1_chebyshev2 = df(x_chebyshev2(1));
dy2_chebyshev2 = df(x_chebyshev2(end));

% Complete splines with specified derivatives at endpoints ("clamped")
sc_equispaced = spline(x_equispaced, [dy1_equispaced, y_equispaced, dy2_equispaced]);
sc_chebyshev2 = spline(x_chebyshev2, [dy1_chebyshev2, y_chebyshev2, dy2_chebyshev2]);


% Basic deBoor splines (equivalent to basic spline usage in MATLAB)
sdb_equispaced = spline(x_equispaced, y_equispaced);
sdb_chebyshev2 = spline(x_chebyshev2, y_chebyshev2);

% Evaluate splines at plotting points
spline_eq = ppval(sc_equispaced, x_range);
spline_ch2 = ppval(sc_chebyshev2, x_range);
deboor_eq = ppval(sdb_equispaced, x_range);
deboor_ch2 = ppval(sdb_chebyshev2, x_range);

% Plot the function and the approximations
figure;
plot(x_range, f(x_range), 'k-', 'LineWidth', 2); hold on;
plot(x_range, spline_eq, 'r--', 'LineWidth', 1.5);
plot(x_range, spline_ch2, 'b-.', 'LineWidth', 1.5);
plot(x_range, deboor_eq, 'g:', 'LineWidth', 1.5);
plot(x_range, deboor_ch2, 'm:', 'LineWidth', 1.5);
scatter(x_equispaced, y_equispaced, 'ro');
scatter(x_chebyshev2, y_chebyshev2, 'bs');
legend('Original function', 'Complete - Equispaced', 'Complete - Chebyshev #2', 'deBoor - Equispaced', 'deBoor - Chebyshev #2', 'Location', 'best');
title('Function Approximation using Splines');
xlabel('x');
ylabel('f(x)');
grid on;

% Part (b) - Discrete least squares with Chebyshev #1 Nodes
x_chebyshev1 = cos(pi*(0:n-1)/(n-1));  % Chebyshev Type 1 nodes
X = cos(pi*(0:n-1)'*(0:n-1)/(n-1));  % Vandermonde matrix for Chebyshev
y_chebyshev1 = f(x_chebyshev1);

% Least squares solution to approximate the function
coeffs = X\y_chebyshev1';
lsq_cheb1 = polyval(flipud(coeffs), x_range);

% Plot the function and the approximations
figure;
plot(x_range, f(x_range), 'k-', 'LineWidth', 2); hold on;
plot(x_range, lsq_cheb1, 'g:', 'LineWidth', 1.5);
scatter(x_chebyshev1, y_chebyshev1, 'g^');
legend('Original function','LSQ - Chebyshev #1', 'Location', 'Best');
title('Function Approximation using LSQ');
xlabel('x');
ylabel('f(x)');
grid on;
