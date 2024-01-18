import math
import torch
import numpy as np


class SoftmaxClassifier:
    def __init__(self, input_shape, num_classes):
        self.input_shape = input_shape
        self.num_classes = num_classes
        self.W = None
        self.initialize()

    def initialize(self):
        # TODO your code here
        # initialize the weight matrix (remember the bias trick) with small random variables
        # you might find torch.randn userful here *0.001
        self.W = torch.randn(self.input_shape, self.num_classes) * 0.001
        # don't forget to set call requires_grad_() on the weight matrix,
        # as we will be be taking its gradients during the learning process
        self.W.requires_grad_()

    def predict_proba(self, X: torch.Tensor) -> torch.Tensor:
        # TODO your code here
        # 0. compute the dot product between the input X and the weight matrix
        # you can use @ for this operation
        scores = X @ self.W
        # remember about the bias trick!
        # 1. apply the softmax function on the scores, see torch.nn.functional.softmax
        # think about on what dimension (dim parameter) you should apply this operation
        scores = torch.nn.functional.softmax(scores, dim=1)
        # 2. returned the normalized scores
        return scores

    def predict(self, X: torch.Tensor) -> torch.Tensor:
        # TODO your code here
        # 0. compute the dot product between the input X and the weight matrix
        scores = X @ self.W
        # 1. compute the prediction by taking the argmax of the class scores
        # you might find torch.argmax useful here.
        # think about on what dimension (dim parameter) you should apply this operation
        label = torch.argmax(scores, dim=1)
        return label

    def cross_entropy_loss(self, y_pred: torch.Tensor, y: torch.Tensor) -> torch.Tensor:
        # TODO your code here
        loss = torch.nn.functional.cross_entropy(y_pred, y)
        return loss

    def log_softmax(self, x: torch.Tensor) -> torch.Tensor:
        return torch.nn.functional.log_softmax(x, dim=1)

    def fit(self, X_train: torch.Tensor, y_train: torch.Tensor,
            **kwargs) -> dict:

        history = []

        bs = kwargs['bs'] if 'bs' in kwargs else 128
        reg_strength = kwargs['reg_strength'] if 'reg_strength' in kwargs else 1e3
        epochs = kwargs['epochs'] if 'epochs' in kwargs else 100
        lr = kwargs['lr'] if 'lr' in kwargs else 1e-3
        print('hyperparameters: lr {:.4f}, reg {:.4f}, epochs {:.2f}'.format(lr, reg_strength, epochs))

        for epoch in range(epochs):
            # in batches of size bs
            for ii in range((X_train.shape[0] - 1) // bs + 1):  
                # TODO your code here
                # we are ii batches in, each of size bs
                start_idx = ii * bs

                # get bs examples
                end_idx = (ii + 1) * bs

                # get the training training examples xb, and their coresponding annotations
                xb = X_train[start_idx:end_idx]
                yb = y_train[start_idx:end_idx]

                # apply the linear layer on the training examples from the current batch
                pred = self.predict_proba(xb)

                # compute the loss function
                # also add the L2 regularization loss (the sum of the squared weights)
                l2_reg = 0.5 * reg_strength * torch.sum(self.W**2)
                loss = self.cross_entropy_loss(pred, yb) + l2_reg
                history.append(loss.detach().numpy())

                # start backpropagation: calculate the gradients with a backwards pass
                loss.backward()

                # update the parameters
                with torch.no_grad():
                    # take a step in the negative direction of the gradient, the learning rate defines the step size
                    self.W -= self.W.grad * lr

                    # ATTENTION: you need to explictly set the gradients to 0 (let pytorch know that you are done with them).
                    self.W.grad.zero_()

        return history

    def get_weights(self, img_shape) -> np.ndarray:
        # TODO your code here
        W = self.W.detach().numpy()
        # 0. ignore the bias term
        W = W[:-1, :]
        # 1. reshape the weights to (*image_shape, num_classes)
        W = W.reshape((*img_shape, self.num_classes))
        # you might find the transpose function useful here
        W = np.transpose(W)
        return W

    def load(self, path: str) -> bool:
        # TODO your code here
        # load the input shape, the number of classes and the weight matrix from a file
        # you might find torch.load useful here
        model_state = torch.load(path)
        self.W = model_state['weights']

        # don't forget to set the input_shape and num_classes fields
        self.num_classes = model_state['num_classes']
        self.input_shape = model_state['input_shape']

        return True

    def save(self, path: str) -> bool:
        # TODO your code here
        # save the input shape, the number of classes and the weight matrix to a file
        # you might find torch useful for this
        # TODO your code here
        model_state = {
            'input_shape': self.input_shape,
            'num_classes': self.num_classes,
            'weights': self.W
        }
        torch.save(model_state, path)
        return True