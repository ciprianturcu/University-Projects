% Define the integrand for J0(x)
integrand = @(theta, x) cos(x .* sin(theta));

% Adaptive quadrature method for J0(x)
J0_adaptive = @(x) (1/pi) * adaptiveQuadrature(@(theta) integrand(theta, x), 0, pi, 1e-10, @trapezoidalRule);

% Romberg's method
J0_romberg = @(x) (1/pi) * romberg(@(theta) integrand(theta, x), 0, pi, 1e-10);

% Calculate J0(x) for x = 1.0, 2.0, 3.0
x_values = [1.0, 2.0, 3.0];
J0_adaptive_values = arrayfun(J0_adaptive, x_values);
J0_romberg_values = arrayfun(J0_romberg, x_values);
J0_matlab_values = besselj(0, x_values);

% Display results
fprintf('x\tJ0 (adaptive)\tJ0 (Romberg)\tJ0 (MATLAB)\n');
for i = 1:length(x_values)
    fprintf('%.1f\t%.6f\t\t%.6f\t\t%.6f\n', x_values(i), J0_adaptive_values(i), J0_romberg_values(i), J0_matlab_values(i));
end

