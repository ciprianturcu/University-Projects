function x=sorMethodv2(A,b,w,era,err,Nmax)
[~,n]=size(A);
if nargin<6, Nmax=50; end
if nargin<5, err=0; end
if nargin<4, era=1e-3; end
if nargin<3, w=1; end
xp = zeros(n,1); 
xc = xp;
for k=1:Nmax
    xc(1)=(1-w)*xp(1)+w/A(1,1)*(b(1)-A(1,2)*xp(2)- A(1, n) * xp(n));
    for i=2:(n/2-1)
        xc(i) =(1-w)*xp(i) + w/A(i,i) * (b(i) - A(i,i-1) * xc(i-1) - A(i,i+1) * xp(i+1) - A(i, n-i+1) * xc(n-i+1));
    end
    xc(n/2) = 1/A(n/2, n/2) * (b(n/2) - A(n/2, n/2-1) * xp(n/2-1) - A(n/2, n/2+1) * xp(n/2+1));
    xc(n/2+1) = 1/A(n/2+1, n/2+1) * (b(n/2+1) - A(n/2+1, n/2) * xp(n/2) - A(n/2+1, n/2+2) * xp(n/2+2));
    for i=(n/2+2):n-1
        xc(i) = (1-w)*xp(i) + w / A(i,i) * (b(i) - A(i,i-1) * xc(i-1) - A(i,i+1) * xp(i+1) - A(i, n-i+1) * xc(n-i+1));
    end
    xc(n) = (1-w)* xp(n) + w/ A(n,n) * (b(n) - A(n,n-1) * xp(n-1) - A(n, 1) * xc(1));
    if norm(xc-xp,inf)<era+err*norm(xc,inf)
        x=xc;
        return;
    end
    xp=xc;
end
error('prea multe iteratii')