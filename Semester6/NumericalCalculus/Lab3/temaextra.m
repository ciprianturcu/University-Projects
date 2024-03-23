syms t;
% Define the error function integrand
f = exp(-t^2);

% Calculate the Taylor series expansion about zero up to the 10th term
taylorSeries = taylor(f, t, 'ExpansionPoint', 0, 'Order', 14);

% The erf function is defined as 2/sqrt(pi) * integral from 0 to x, so we adjust our series accordingly
taylorSeriesErf = 2/sqrt(pi) * int(taylorSeries, 0, t);

% Simplify the Taylor series for the erf function
taylorSeriesErf = simplify(taylorSeriesErf)

% Evaluate erf(1) using the series expansion
erf1 = double(subs(taylorSeriesErf, t, 1))
