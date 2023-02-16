%ex1 a

command_a1 = input('input the distribution method (normal,student,chi2,fischer) : ','s');
if(command_a1 == "normal")
    mu_a1=input('mu=');
    sigma_a1=input('sigma=');
end
if(command_a1 == "student" || command_a1 == "chi2")
    nu_a1=input('nu=');
end
if(command_a1 == "fischer")
    n_a1=input('n=');
    m_a1=input('m=');
end
switch command_a1
    case 'normal'
        result_a1 = normcdf(0,mu_a1,sigma_a1);
    case 'student'
        result_a1 = tcdf(0,nu_a1);
    case 'chi2'
        result_a1 = chi2cdf(0,nu_a1);
    case 'fischer'
        result_a1 = fcdf(0,n_a1,m_a1);
    otherwise
        fprintf('wasn t valid');
end
command_a2 = input('quantile distribution : ','s');
if(command_a2 == "normal")
    mu_a2=input('mu=');
    sigma_a2=input('sigma=');
end
if(command_a2 == "student" || command_a2 == "chi2")
    nu_a2=input('nu=');
end
if(command_a2 == "fischer")
    n_a2=input('n=');
    m_a2=input('m=');
end
switch command_a2
    case 'normal'
        result_a2 = norminv(0,mu_a2,sigma_a2);
    case 'student'
        result_a2 = tinv(0,nu_a2);
    case 'chi2'
        result_a2 = chi2inv(0,nu_a2);
    case 'fischer'
        result_a2 = finv(0,n_a2,m_a2);
    otherwise
        fprintf('wasn t valid');
end
    
fprintf("P(X<=0) = %1.6f\n", result_a1);
fprintf("P(X>=0) = %1.6f\n",result_a2);

%ex1 b

command_a3 = input('input the distribution method (normal,student,chi2,fischer) : ','s');
if(command_a3 == "normal")
    mu_a3=input('mu=');
    sigma_a3=input('sigma=');
end
if(command_a3 == "student" || command_a3 == "chi2")
    nu_a3=input('nu=');
end
if(command_a3 == "fischer")
    n_a3=input('n=');
    m_a3=input('m=');
end
switch command_a3
    case 'normal'
        result_a3 = normcdf(1,mu_a3,sigma_a3)-normcdf(-1,mu_a3,sigma_a3);
    case 'student'
        result_a3 = tcdf(1,nu_a3)-tcdf(-1,nu_a3);
    case 'chi2'
        result_a3 = chi2cdf(1,nu_a3)-chi2cdf(-1,nu_a3);
    case 'fischer'
        result_a3 = fcdf(1,n_a3,m_a3)-fcdf(-1,n_a3,m_a3);
    otherwise
        fprintf('wasn t valid');
end

fprintf("P(-1<=x<=1) = %1.6f\n", result_a3);
fprintf("P(X<=-1 || X>=1) = %1.6f\n", 1-result_a3);
%ex1 c

command_a4 = input('input the distribution method (normal,student,chi2,fischer) : ','s');
alpha = input('alpha=');
if(command_a4 == "normal")
    mu_a4=input('mu=');
    sigma_a4=input('sigma=');
end
if(command_a4 == "student" || command_a4 == "chi2")
    nu_a4=input('nu=');
end
if(command_a4 == "fischer")
    n_a4=input('n=');
    m_a4=input('m=');
end
switch command_a4
    case 'normal'
        result_a4 = norminv(alpha,mu_a4,sigma_a4);
    case 'student'
        result_a4 = tcdf(alpha,nu_a4);
    case 'chi2'
        result_a4 = chi2cdf(alpha,nu_a4);
    case 'fischer'
        result_a4 = fcdf(alpha,n_a4,m_a4);
    otherwise
        fprintf('wasn t valid');
end
fprintf("xc = %1.6f\n",result_a4);
beta = input('beta=');
if(command_a4 == "normal")
    mu_a5=input('mu=');
    sigma_a5=input('sigma=');
end
if(command_a4 == "student" || command_a4 == "chi2")
    nu_a5=input('nu=');
end
if(command_a4 == "fischer")
    n_a5=input('n=');
    m_a5=input('m=');
end
switch command_a4
    case 'normal'
        result_a5 = norminv(1-beta,mu_a5,sigma_a5);
    case 'student'
        result_a5 = tinv(1-beta,nu_a5);
    case 'chi2'
        result_a5 = chi2inv(1-beta,nu_a5);
    case 'fischer'
        result_a5 = finv(1-beta,n_a5,m_a5);
    otherwise
        fprintf('wasn t valid');
end

fprintf("xd = %1.6f\n",result_a5);

%ex2 a
clf
p=input("p=");
for i = 1 : 10
  n = i*5;
  v = 0 : n;
  plot(v, binopdf(v, n, p))
  pause(1);
end

%ex2 b
clf
p = input("p=");
n = input("n=");
v = 0 : n;
plot(v, binopdf(v, n, p), 'm', v, poisspdf(v, n*p), 'b')
