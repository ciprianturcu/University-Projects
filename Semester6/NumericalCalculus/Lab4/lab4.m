A = rand(7) +2*eye();
A([3,5],1) = A([5,3],1);
b=A*(1:7)';
cond(A);
s = gaussianElimination(A, b);
