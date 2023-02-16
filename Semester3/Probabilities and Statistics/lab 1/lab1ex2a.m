x=0:0.1:3;
 y=x.^5/10;
 p=x.*sin(x);
 t=cos(x);
 plot(x,y,'--rs',x,p,'.-g',x,t,'-.b')
 title("graph1")
 legend('y = x^5/10', 'p = x*sin(x)', 't = cosx')
 