% x | 0 | pi/6 | pi/4 | pi/3 | pi/2
%sinx | 0 | 1/2 | sqrt2/2 | 

x=1:3
y=cos(x)
v=lagrangeInterpolation(x,y,line(1,3,10))


% de aici x are valorile din poza
t=pi/18
err=prod(t-x)/factorial(5)

