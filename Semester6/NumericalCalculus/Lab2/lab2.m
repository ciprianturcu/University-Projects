% Include the symbolic package
syms x;

% Define the function e^x symbolically
f = exp(x);

% Generate Taylor polynomials of degrees 1, 2, 3, and 4 around 0
p1 = taylor(f, x, 'Order', 2); % Degree 1 polynomial requires Order 2
p2 = taylor(f, x, 'Order', 3);
p3 = taylor(f, x, 'Order', 4);
p4 = taylor(f, x, 'Order', 5);

% Convert symbolic expressions to anonymous functions for plotting
f_anon = matlabFunction(f);
p1_anon = matlabFunction(p1);
p2_anon = matlabFunction(p2);
p3_anon = matlabFunction(p3);
p4_anon = matlabFunction(p4);

% Define the range for x for plotting
x_plot = linspace(-3, 3, 400);

% Plotting
figure;
plot(x_plot, f_anon(x_plot), 'LineWidth', 2, 'DisplayName', 'e^x');
hold on;
plot(x_plot, p1_anon(x_plot), '--', 'DisplayName', 'Degree 1');
plot(x_plot, p2_anon(x_plot), '--', 'DisplayName', 'Degree 2');
plot(x_plot, p3_anon(x_plot), '--', 'DisplayName', 'Degree 3');
plot(x_plot, p4_anon(x_plot), '--', 'DisplayName', 'Degree 4');
hold off;

title('Function e^x and its Taylor Polynomials');
xlabel('x');
ylabel('y');
legend('show');
grid on;
