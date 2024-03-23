
%a)
syms x
f=exp(x);
f1=taylor(f,x,0,'order',2);
f2=taylor(f,x,0,'order',3);
f3=taylor(f,x,0,'order',4);
f4=taylor(f,x,0,'order',5);

ezplot(f,[-3,3])
hold on
ezplot(f1,[-3,3])
hold on
ezplot(f2,[-3,3])
hold on
ezplot(f3,[-3,3])
hold on

%b)
exp(1) 
vpa(exp(1),7) %2.718282
subs(f1,x,1)
vpa(subs(f4,x,1),7) 

for k=6:20
    T=taylor(f,x,0,'order',k);
    k
    vpa(subs(T,x,1),7)
end

%we get 2.718282 at k=10 so the degree of the polynomial is 9
