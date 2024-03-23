A = rand(4, 4); % 4x4 matrix
b = rand(4, 1); % 4x1 vector
time = estimateCramerTime(A, b);
disp(['Time taken: ', num2str(time), ' seconds']);
