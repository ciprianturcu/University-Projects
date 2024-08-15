% Verify Boole's Rule using Romberg's method

% Define the function
f = @(x) log(x);

% Integration bounds
a = 1;
b = 2;

% Calculate integral using Boole's rule
[boole_result, nfev_boole] = booleRule(f, a, b);

% Calculate integral using Romberg's method
tol = 1e-9;
romberg_result = romberg(f, a, b, tol);

% Calculate the exact value of the integral
exact_value = integral(f, a, b);

% Print results
fprintf('Boole Rule Result: %.10f, Function Evaluations: %d\n', boole_result, nfev_boole);
fprintf('Romberg Method Result: %.10f\n', romberg_result);
fprintf('Exact Value: %.10f\n', exact_value);
