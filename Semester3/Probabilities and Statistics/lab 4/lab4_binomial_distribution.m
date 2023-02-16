clear ALL
n=input("number of trials :");
p=input("p in (0,1) :");

%U=rand(1,n);
%X=(U<p);

N=input("nr of simulations :");
for i=1:N
    U=rand(n,1);
    X(i) = sum(U < p);
end

X

U_X = unique(X)
n_X = hist(X,length(U_X));
frequency = n_X/N

k= 0:n;
y=binopdf(k,n,p);
clf;
plot(k,y,'bo',U_X,frequency,'rx', 'MarkerSize', 10);
legend('binopdf', 'simulation');


