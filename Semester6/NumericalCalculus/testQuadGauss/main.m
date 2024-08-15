% Problem 1
[I_sin, I_cos, err_sin, err_cos] = problem1();
fprintf('Problem 1:\n');
fprintf('Integral of sin(t^2): %f, Error: %e\n', I_sin, err_sin);
fprintf('Integral of cos(t^2): %f, Error: %e\n', I_cos, err_cos);

% Problem 2
[I2, err2] = problem2();
fprintf('Problem 2:\n');
fprintf('Integral: %f, Error: %e\n', I2, err2);

% Problem 3
[I3, err3] = problem3();
fprintf('Problem 3:\n');
fprintf('Integral: %f, Error: %e\n', I3, err3);

% Problem 4
[I4, err4] = problem4();
fprintf('Problem 4:\n');
fprintf('Integral: %f, Error: %e\n', I4, err4);

% Problem 5
x = 2;
y = 5;
[I5, err5] = problem5(x, y);
fprintf('Problem 5:\n');
fprintf('Integral: %f, Error: %e\n', I5, err5);

% Problem 6
[I6, err6] = problem6();
fprintf('Problem 6:\n');
fprintf('Integral: %f, Error: %e\n', I6, err6);
