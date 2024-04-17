function v = lagrangeInterpolation(x, y, t)
%LAGRANGE - computer language interpolation polynomial
%x - interpolation nodes
%y - function values
%t - evaluation points
    x = x(:); 
    y = y(:); 
    t = t(:);

    n = length(x);
    v = zeros(size(t));
    
    for k = 1:length(t)
        z = ones(m);
        for j=[1:i-1,i+1:n]
            z = z.*(t-x(j))/(x(i)-x(j));
        end
        v = v + z * y(i);
    end
end
