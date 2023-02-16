%Binomial distributionwith parameters 3 and 1/2
x = 0:3;
ans = pdf('bino',x, 3, 1/2);
plot(x,ans,"r")
hold on

xx=0:0.01:3;
fx=cdf('Binomial',xx, 3, 1/2);
plot(xx,fx,"m")

p1=binopdf(0,3,1/2);
fprintf("P(X=0) = %1.6f\n", p1);

p2=1-binopdf(0,3,1/2);
fprintf("P(X!=0) = %1.6f\n",p2);

p3=binopdf(2,3,1/2);
fprintf("P(X<=2) = %1.6f\n", p3);

p4=1-binopdf(2,3,1/2);
fprintf("P(X<2) = %1.6f\n", p4);

p5=1 - binopdf(1,3,1/2);
fprintf("P(X>=1) = %1.6f\n", p5);

x1=randi([0,1],1,3)




