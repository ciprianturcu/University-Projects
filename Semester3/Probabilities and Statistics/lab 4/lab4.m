p=input('p in (0,1) =');
U=rand;
X=(U<p);

N=input('number of simulations =');

U=rand(1,N);
X=(U<p);

%for i=1:N
%    U=rand;
%    X(i)=(U<p);
%end

U_X = unique(X);
n_X = hist(X,length(U_X));
relative_freq = n_X/N;

[U_X ; relative_freq]