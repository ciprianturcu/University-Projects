clf
%a)
syms x
fs=sin(x);
fs3=taylor(fs,x,0,'order',4);
fs5=taylor(fs,x,0,'order',6);

ezplot(fs,[-pi,pi])
hold on
ezplot(fs3,[-pi,pi])
hold on
ezplot(fs5,[-pi,pi])

%b)
vpa(sin(pi/5),5) %0.58779

for k=1:20
    T=taylor(fs,x,0,'order',k);
    k
    vpa(subs(T,x,pi/5),5)
end
%k=9 so degree is 5

%c)

vpa(sin((10*pi)/3),5) %-0.86603

for l=1:40
    T=taylor(fs,x,0,'order',l);
    l
    vpa(subs(T,x,(10*pi)/3),5)
end
%l=36


for m=1:20
    T=taylor(fs,x,0,'order',m);
    m
    vpa(subs(T,x,(-pi)/3),5)
end
%m=10