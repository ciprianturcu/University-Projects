%Matrix initialization
A = [10, 7, 8, 7; 7, 5, 6, 5; 8, 6, 10, 9; 7, 5, 9, 10];
b = [32, 23, 33, 31]';

%point a
x = inv(A) * b;
disp('System solution:');
disp(x);


%point b
bt = [32.1, 22.9, 33.1, 30.9]';

xt = inv(A) * bt;

err1 = norm(b - bt) / norm(b);
disp('Input relative error:')
disp(err1);
err2 = norm(x - xt) / norm(x);
disp('Output relative error:')
disp(err2);


%point c
At = [10, 7, 8.1, 7.2; 7.8, 5.04, 6, 5; 8, 5.98, 9.89, 9; 6.99, 4.99, 9, 9.98];

err3 = norm(A - At) / norm(A);
disp('Input relative error:')
disp(err3);
xt2 = inv(At) * b;
err4 = norm(x - xt2) / norm(x);
disp('Output relative error:')
disp(err4);

%point d
condA = norm(A) * norm(inv(A));

% Calculation of the condition number
condA = norm(A) * norm(inv(A));

% Interpretation based on the condition number value
if condA < 10
    fprintf('The matrix A is well-conditioned with a condition number of %.2f. Solutions should be stable and reliable.\n', condA);
elseif condA < 100
    fprintf('The matrix A is moderately conditioned with a condition number of %.2f. Caution is advised as solutions may begin to show sensitivity to changes in A or b.\n', condA);
else
    fprintf('The matrix A is ill-conditioned with a condition number of %.2f. Solutions are highly sensitive to changes in A or b, and numerical results may be unreliable.\n', condA);
end
