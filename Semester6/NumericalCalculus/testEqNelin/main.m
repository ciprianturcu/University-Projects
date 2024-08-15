f = @(x) x - cos(x);
fd = @(x) 1 + sin(x);

% Initial guesses and parameters
x0_newton = pi/4; % Initial guess for Newton's method
x0 = 0.5; % Initial guess for successive approximation
x1 = 0.6; % Second initial guess for secant method
ea = 1e-10; % Absolute error tolerance
er = 1e-10; % Relative error tolerance
nmax = 100; % Maximum number of iterations

%Newton's method
try
    [z_newton, ni_newton] = Newton(f, fd, x0_newton, ea, er, nmax);
    fprintf('Newton method: root = %f, iterations = %d\n', z_newton, ni_newton);
catch e
    fprintf('Newton method error: %s\n', e.message);
end

%secant method
try
    [z_secant, ni_secant] = secant(f, x0, x1, ea, er, nmax);
    fprintf('Secant method: root = %f, iterations = %d\n', z_secant, ni_secant);
catch e
    fprintf('Secant method error: %s\n', e.message);
end

% method of successive approximations
try
    g = @(x) cos(x); % Iterative function for successive approximations
    [z_mas, ni_mas] = mas(g, x0, ea, er, nmax);
    fprintf('Method of successive approximations: root = %f, iterations = %d\n', z_mas, ni_mas);
catch e
    fprintf('Method of successive approximations error: %s\n', e.message);
end