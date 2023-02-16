clear ALL
p=input('p in (0,1):')
X=0;
while(X >= p)
    X=X+1;
end;
clear X;

N= input('number of simulations = ');
for i = 1:N
    X(i) = 0;
    while(rand >= p)
        X(i) = X(i)+1;
    end;
end

U_X = unique(X);
n_X = hist(X,length(U_X));
frequency = n_X/N;

k=0:20;
y=geopdf(k,p);
plot(k,y,'bo',U_X,frequency,'rx', 'MarkerSize', 10);
legend('geopdf', 'simulation');