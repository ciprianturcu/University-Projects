import os
import sys
import torch
import cifar10
import numpy as np
from functools import reduce
import matplotlib.pyplot as plt
from lab2.softmax import SoftmaxClassifier

cifar_root_dir = 'cifar-10-batches-py'

# load cifar10 dataset
X_train, y_train, X_test, y_test = cifar10.load_ciaf10(cifar_root_dir)

# convert the training and test data to floating point
X_train = X_train.astype(np.float32)
X_test = X_test.astype(np.float32)

# Reshape the training data such that we have one image per row
X_train = np.reshape(X_train, (X_train.shape[0], -1))
X_test = np.reshape(X_test, (X_test.shape[0], -1))

# pre-processing: subtract mean image
mean_image = np.mean(X_train, axis=0)
X_train -= mean_image
X_test -= mean_image

# Bias trick - add 1 to each training example
X_train = np.hstack([X_train, np.ones((X_train.shape[0], 1))])
X_test = np.hstack([X_test, np.ones((X_test.shape[0], 1))])

# convert everything to tensors
X_train, y_train, X_test, y_test = map(
    torch.tensor, (X_train, y_train, X_test, y_test)
)

X_train = X_train.float()
X_test = X_test.float()


if not os.path.exists('train'):
    os.mkdir('train')

best_acc = -1
best_cls_path = ''


input_size_flattened = reduce((lambda a, b: a * b), X_train[0].shape)

# the batch size
batch_size = 200
# number of training steps per training process
train_epochs = 50


lr = 0.007 # change the value - hyperparameter tuning
reg_strength = 0.007 # change the value - hyperparameter tuning

cls = SoftmaxClassifier(input_shape=input_size_flattened, num_classes=cifar10.NUM_CLASSES)
history = cls.fit(X_train, y_train, lr=lr, reg_strength=reg_strength,
        epochs=train_epochs, bs=batch_size)

with torch.no_grad():
  y_train_pred = cls.predict(X_train)
  y_val_pred = cls.predict(X_test)

train_acc = torch.mean((y_train == y_train_pred).float())

test_acc = torch.mean((y_test == y_val_pred).float())
sys.stdout.write('\rlr {:.4f}, reg_strength{:.2f}, test_acc {:.2f}; train_acc {:.2f}'.format(lr, reg_strength, test_acc, train_acc))
cls_path = os.path.join('train', 'softmax_lr{:.4f}_reg{:.4f}-test{:.2f}.npy'.format(lr, reg_strength, test_acc))
cls.save(cls_path)


plt.plot(history)
plt.show()

best_softmax = cls


plt.rcParams['image.cmap'] = 'gray'
# now let's display the weights for the best model
weights = best_softmax.get_weights((32, 32, 3))

w_min = np.amin(weights)
w_max = np.amax(weights)

for idx in range(0, cifar10.NUM_CLASSES):
    plt.subplot(2, 5, idx + 1)
    # normalize the weights
    template = 255.0 * (weights[idx, :, :, :].squeeze() - w_min) / (w_max - w_min)
    template = template.astype(np.uint8)
    plt.imshow(template)
    plt.title(cifar10.LABELS[idx])

plt.show()


# TODO your code here
# use the metrics module to compute the precision, recall and confusion matrix for the best classifier
# end TODO your code here
