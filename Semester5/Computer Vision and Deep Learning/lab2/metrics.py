import numpy as np


def confusion_matrix(y_true: np.ndarray, y_pred: np.ndarray, num_classes = None) -> np.ndarray:
    """"
    Computes the confusion matrix from labels (y_true) and predictions (y_pred).
    The matrix columns represent the prediction labels and the rows represent the ground truth labels.
    The confusion matrix is always a 2-D array of shape `[num_classes, num_classes]`,
    where `num_classes` is the number of valid labels for a given classification task.
    The arguments y_true and y_pred must have the same shapes in order for this function to work

    num_classes represents the number of classes for the classification problem. If this is not provided,
    it will be computed from both y_true and y_pred
    """
    conf_mat = None
    # TODO your code here - compute the confusion matrix
    # even here try to use vectorization, so NO for loops

    # 0. if the number of classes is not provided, compute it based on the y_true and y_pred arrays
    if num_classes is None:
      num_classes = np.max([np.max(y_true), np.max(y_pred)]) + 1

    # 1. create a confusion matrix of shape (num_classes, num_classes) and initialize it to 0
    conf_mat = np.zeros((num_classes, num_classes), dtype=np.int)

    # 2. use argmax to get the maximal prediction for each sample
    # hint: you might find np.add.at useful: https://numpy.org/doc/stable/reference/generated/numpy.ufunc.at.html
    np.add.at(conf_mat, (y_true, y_pred), 1)

    # end TODO your code here
    return conf_mat


def precision_score(y_true: np.ndarray, y_pred: np.ndarray, num_classes=None) -> float:
    """"
    Computes the precision score.
    For binary classification, the precision score is defined as the ratio tp / (tp + fp)
    where tp is the number of true positives and fp the number of false positives.

    For multiclass classification, the precision and recall scores are obtained by summing over the rows / columns
    of the confusion matrix.

    num_classes represents the number of classes for the classification problem. If this is not provided,
    it will be computed from both y_true and y_pred
    """
    precision = 0

    # TODO your code here
    conf_mat = confusion_matrix(y_true, y_pred, num_classes)
    true_positives = np.diag(conf_mat)
    false_positives = np.sum(conf_mat, axis=0) - true_positives

    # update precision calculation to handle division by zero
    precision = np.zeros(num_classes)
    non_zero_denominator = (true_positives + false_positives) > 0
    precision[non_zero_denominator] = true_positives[non_zero_denominator] / (true_positives[non_zero_denominator] + false_positives[non_zero_denominator])
    
    # for cases where the denominator is zero
    precision[~non_zero_denominator] = 1

    precision = np.sum(precision) / num_classes
    # end TODO your code here

    return precision


def recall_score(y_true: np.ndarray, y_pred: np.ndarray, num_classes=None)  -> float:
    """"
    Computes the recall score.
    For binary classification, the recall score is defined as the ratio tp / (tp + fn)
    where tp is the number of true positives and fn the number of false negatives

    For multiclass classification, the precision and recall scores are obtained by summing over the rows / columns
    of the confusion matrix.

    num_classes represents the number of classes for the classification problem. If this is not provided,
    it will be computed from both y_true and y_pred
    """
    recall = None

    # TODO your code here
    conf_mat = confusion_matrix(y_true, y_pred, num_classes)
    true_positives = np.diag(conf_mat)
    false_negatives = np.sum(conf_mat, axis=1) - true_positives
    recall = np.sum(true_positives / (true_positives + false_negatives)) / num_classes
    # end TODO your code here

    return recall


def accuracy_score(y_true: np.ndarray, y_pred: np.ndarray) -> float:
    acc_score = 0

    # TODO your code here
    # remember, use vectorization, so no for loops
    # hint: you might find np.trace useful here https://numpy.org/doc/stable/reference/generated/numpy.trace.html
    conf_mat = confusion_matrix(y_true, y_pred)
    acc_score = np.trace(conf_mat) / np.sum(conf_mat)
    # end TODO your code here

    return acc_score


def f1_score(y_true: np.ndarray, y_pred: np.ndarray, num_classes=None) -> float:
    """"
    Computes the F1-score.
    The F1-score is the harmonic mean of precision and recall.

    num_classes represents the number of classes for the classification problem. If this is not provided,
    it will be computed from both y_true and y_pred
    """
    precision = precision_score(y_true, y_pred, num_classes)
    recall = recall_score(y_true, y_pred, num_classes)

    f1 = 2 * (precision * recall) / (precision + recall) if (precision + recall) > 0 else 0

    return f1


if __name__ == '__main__':
    # TODO your tests here
    # add some test for your code.
    # you could use the sklean.metrics module (with macro averaging to check your results)
    y_true = np.array([0, 1, 2, 0, 1, 2, 0, 1, 2])
    y_pred = np.array([0, 1, 2, 1, 0, 2, 0, 2, 1])

    print("Confusion Matrix:")
    print(confusion_matrix(y_true, y_pred))

    print("\nPrecision Score:")
    print(precision_score(y_true, y_pred))

    print("\nRecall Score:")
    print(recall_score(y_true, y_pred))

    print("\nAccuracy Score:")
    print(accuracy_score(y_true, y_pred))

    print("\nF1 Score:")
    print(f1_score(y_true, y_pred))