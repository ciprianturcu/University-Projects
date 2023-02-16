clear ALL
n = input('number of success = ');
p = input('probability of success = ');
for j = 1:n
    Y(j) = 0;
    while(rand >= p)
        Y(j) = Y(j)+1;
    end;
end
X = sum(Y);
clear X;

N = input('number of simulations = ');
for i = 1:N
    for j = 1:n
        Y(j) = 0;
        while(rand >= p)
            Y(j) = Y(j)+1;
        end;
    end
    X(i) = sum(Y);
end

U_X = unique(X);
n_X = hist(X,length(U_X));
frequency = n_X/N;

k=0:150;
y=nbinpdf(k,n,p);
plot(k,y,'bo',U_X,frequency,'rx', 'MarkerSize', 10);
legend('nbinpdf', 'simulation');