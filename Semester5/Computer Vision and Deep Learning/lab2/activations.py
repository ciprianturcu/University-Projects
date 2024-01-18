import numpy as np

def softmaxtemperature(x, T=1,recover_probs=True):
    """
    Applies the softmax temperature on the input x, using the temperature t.
    If recover_probs is True, returns the recovered probabilities, otherwise returns the log probabilities.
    """

    max_x = np.max(x)
    exp_x = np.exp((x - max_x)/T)
    sum_exp_x = np.sum(exp_x)
    log_sum_exp_x = np.log(sum_exp_x)
    max_plus_log_sum_exp_x = max_x + log_sum_exp_x
    log_probs = x - max_plus_log_sum_exp_x

    # Recover probs
    if recover_probs:
        exp_log_probs = np.exp(log_probs)
        sum_log_probs = np.sum(exp_log_probs)
        probs = exp_log_probs / sum_log_probs
        return probs

    return log_probs

def softmax(x, T=1):
    exp_x = np.exp(x/T)
    sum_exp_x = np.sum(exp_x)
    sm_x = exp_x/sum_exp_x
    return sm_x