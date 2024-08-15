function [result, function_evals] = adaptiveQuadrature(f, a, b, tolerance, rule)
    % Initial subdivision
    initial_subdivisions = 4;
    [approx1, evals1] = rule(f, a, b, initial_subdivisions);
    [approx2, evals2] = rule(f, a, b, 2 * initial_subdivisions);
    
    % Total function evaluations so far
    function_evals = evals1 + evals2;
    
    % Check if the approximation is within the desired tolerance
    if abs(approx1 - approx2) < tolerance
        result = approx2;
    else
        % Recursive subdivision
        midpoint = (a + b) / 2;
        [left_result, left_evals] = adaptiveQuadrature(f, a, midpoint, tolerance, rule);
        [right_result, right_evals] = adaptiveQuadrature(f, midpoint, b, tolerance, rule);
        
        % Combine results
        result = left_result + right_result;
        function_evals = function_evals + left_evals + right_evals;
    end
end