x=0:0.1:3;
 y=x.^5/10;
 p=x.*sin(x);
 t=cos(x);

 subplot(3,1,1)
 plot(x,y,'--rs')
 legend('y=x^5/10')

 subplot(3,1,2)
 plot(x,p,'--b')
 legend('p=x*sinx')

 subplot(3,1,3)
 plot(x,t,'--g')
 legend('t= cosx')