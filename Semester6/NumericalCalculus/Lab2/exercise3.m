clc
%a)
fl=log(1+x);
fl3=taylor(fl,x,0,'order',3);
fl5=taylor(fl,x,0,'order',6);

ezplot(fl,[-0.9,1])
hold on
ezplot(fl3,[-0.9,1])
hold on
ezplot(fl5,[-0.9,1])

%b)
vpa(log(1+1),5) %0.69315

for k=1:20
    T=taylor(fl,x,0,'order',k);
    k
    vpa(subs(T,x,1),5)
end
%it s impossible to approximate

% )
ffl=log((1+x)/(1-x));
%e)
for k=5:20
    T1=taylor(log(1-x),x,0,'order',k);
    T2=taylor(log(1+x),x,0,'order',k);
    k
    vpa(subs(T2-T1,x,1/3),5)
end

%0.69315 we get this for k=10