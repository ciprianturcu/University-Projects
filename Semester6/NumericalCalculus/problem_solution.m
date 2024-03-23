% Script for Problem 1 and Problem 2 solutions

% Problem 2 Part a) Plot the function over the interval [-1e-11, 1e-11]
x = linspace(-1e-11, 1e-11, 1000);
f_original = (exp(x) - 1 - x) ./ x.^2;
figure;
plot(x, f_original);
title('Original f(x) over [-1e-11, 1e-11]');
xlabel('x');
ylabel('f(x)');

% Problem 2 Part d) Plot the improved function for |x| < 1
x_improved = linspace(-0.1, 0.1, 1000);
f_improved = arrayfun(@(x) improvedComputeF(x), x_improved);
figure;
plot(x_improved, f_improved);
title('Improved f(x) for |x| < 1');
xlabel('x');
ylabel('f(x)');

% Function Definitions
% Define the function 'evaluateSeries' for Problem 1
function result = evaluateSeries(x)
    if x <= 0 || x >= 25
        error('x must be in the range (0, 25)');
    end
    
    term = x^2; % Initialize the first term of the series for n=1
    sum = term; % The sum starts with the first term
    n = 1; % Counter for n starts at 1
    while true
        n = n + 1; % Increment n
        term = term * x^2 / ((2*n^2 - 1) * n); % Calculate the next term in the series
        if abs(term) < eps * abs(sum) % Check for convergence
            break; % Break if the next term does not significantly change the sum
        end
        sum = sum + term; % Add the current term to the sum
    end
    result = sum; % Return the sum as the result
end

% Define the function 'improvedComputeF' for Problem 2 Part c)
function result = improvedComputeF(x)
    if abs(x) < eps % Use Taylor expansion for very small x to avoid numerical issues
        result = 1/2 + x/6; % Use the first terms of the Taylor expansion of (e^x - 1 - x) / x^2
    else
        result = (exp(x) - 1 - x) / x^2;
    end
end
