% point_c.m
% Script for error analysis and log-log plot for Boole's Rule and Romberg's Method

% Define the function
f = @(x) log(x);

% Integration bounds
a = 1;
b = 2;

% Calculate the exact value of the integral
exact_value = integral(f, a, b);

% Initialize arrays for storing errors and step sizes
errors_boole = [];
errors_romberg = [];
h_values = [];

% Loop over different step sizes
for i = 0:9
    % Calculate step size
    h = (b - a) / 2^i;
    h_values = [h_values, h];
    
    % Boole's rule integration (fixed interval)
    [boole_result, ~] = booleRule(f, a, b);
    errors_boole = [errors_boole, abs(boole_result - exact_value)];
    
    % Romberg's method integration (variable interval)
    romberg_result = romberg(f, a, b, h);
    errors_romberg = [errors_romberg, abs(romberg_result - exact_value)];
end

% Log-log plot of the error
figure;
loglog(h_values, errors_boole, '-o', h_values, errors_romberg, '-x');
xlabel('Step size (h)');
ylabel('Error');
legend('Boole Rule', 'Romberg Method');
title('Log-Log Plot of the Error');
grid on;
