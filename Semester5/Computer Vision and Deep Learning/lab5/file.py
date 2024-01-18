import hashlib
import os
import tarfile
import cv2
import requests
import wget
import glob
import wandb
import shutil
import numpy as np

import torch
import torch.nn as nn
import torchvision
import matplotlib.pyplot as plt
from torchvision.transforms import v2
from torch.utils.data import DataLoader
from tqdm import tqdm
from PIL import Image

def upsample_block(x, filters, size, stride = 2):
  """
  x - the input of the upsample block
  filters - the number of filters to be applied
  size - the size of the filters
  """

  # TODO your code here
  # transposed convolution
  upsample= torch.nn.ConvTranspose2d(in_channels=x.shape[1], out_channels=filters, kernel_size=size, stride=stride, padding=1)
  x = upsample(x)
  # BN
  bn  = torch.nn.BatchNorm2d(num_features=filters)
  # relu activation
  relu = torch.nn.ReLU()
  x = relu(x)
  return x

class UNet(nn.Module):
    def __init__(self):
        super(UNet, self).__init__()
        # Encoder
        self.ec11 = nn.Conv2d(3, 64, kernel_size=3, padding=1)
        self.ec12 = nn.Conv2d(64, 64, kernel_size=3, padding=1)
        self.mp1 = nn.MaxPool2d(kernel_size=2, stride=2)

        self.ec21 = nn.Conv2d(64, 128, kernel_size=3, padding=1)
        self.ec22 = nn.Conv2d(128, 128, kernel_size=3, padding=1)
        self.mp2 = nn.MaxPool2d(kernel_size=2, stride=2)

        self.ec31 = nn.Conv2d(128, 256, kernel_size=3, padding=1)
        self.ec32 = nn.Conv2d(256, 256, kernel_size=3, padding=1)
        self.mp3 = nn.MaxPool2d(kernel_size=2, stride=2)

        self.ec41 = nn.Conv2d(256, 512, kernel_size=3, padding=1)
        self.ec42 = nn.Conv2d(512, 512, kernel_size=3, padding=1)
        self.mp4 = nn.MaxPool2d(kernel_size=2, stride=2)

        self.ec51 = nn.Conv2d(512, 1024, kernel_size=3, padding=1)
        self.ec52 = nn.Conv2d(1024, 1024, kernel_size=3, padding=1)

        # Decoder
        self.upconv4 = nn.ConvTranspose2d(1024, 512, kernel_size=2, stride=2)
        self.dc41 = nn.Conv2d(1024, 512, kernel_size=3, padding=1)
        self.dc42 = nn.Conv2d(512, 512, kernel_size=3, padding=1)

        self.upconv3 = nn.ConvTranspose2d(512, 256, kernel_size=2, stride=2)
        self.dc31 = nn.Conv2d(512, 256, kernel_size=3, padding=1)
        self.dc32 = nn.Conv2d(256, 256, kernel_size=3, padding=1)

        self.upconv2 = nn.ConvTranspose2d(256, 128, kernel_size=2, stride=2)
        self.dc21 = nn.Conv2d(256, 128, kernel_size=3, padding=1)
        self.dc22 = nn.Conv2d(128, 128, kernel_size=3, padding=1)

        self.upconv1 = nn.ConvTranspose2d(128, 64, kernel_size=2, stride=2)
        self.dc11 = nn.Conv2d(128, 64, kernel_size=3, padding=1)
        self.dc12 = nn.Conv2d(64, 64, kernel_size=3, padding=1)

        self.final_conv = nn.Conv2d(64, 3, kernel_size=1)

    def forward(self, x):
        # Encoder
        ec11_out = nn.ReLU(inplace=True)(self.ec11(x))
        ec12_out = nn.ReLU(inplace=True)(self.ec12(ec11_out))
        pool1_out = self.mp1(ec12_out)

        ec21_out = nn.ReLU(inplace=True)(self.ec21(pool1_out))
        ec22_out = nn.ReLU(inplace=True)(self.ec22(ec21_out))
        pool2_out = self.mp2(ec22_out)

        ec31_out = nn.ReLU(inplace=True)(self.ec31(pool2_out))
        ec32_out = nn.ReLU(inplace=True)(self.ec32(ec31_out))
        pool3_out = self.mp3(ec32_out)

        ec41_out = nn.ReLU(inplace=True)(self.ec41(pool3_out))
        ec42_out = nn.ReLU(inplace=True)(self.ec42(ec41_out))
        pool4_out = self.mp4(ec42_out)

        ec51_out = nn.ReLU(inplace=True)(self.ec51(pool4_out))
        ec52_out = nn.ReLU(inplace=True)(self.ec52(ec51_out))

        #Decoder 
        upconv4_out = self.upconv4(ec52_out)
        cat4 = torch.cat([ec42_out, upconv4_out], dim=1) 
        dc41_out = nn.ReLU(inplace=True)(self.dc41(cat4))
        dc42_out = nn.ReLU(inplace=True)(self.dc42(dc41_out))

        upconv3_out = self.upconv3(dc42_out)
        cat3 = torch.cat([ec32_out, upconv3_out], dim=1) 
        dc31_out = nn.ReLU(inplace=True)(self.dc31(cat3))
        dc32_out = nn.ReLU(inplace=True)(self.dc32(dc31_out))

        upconv2_out = self.upconv2(dc32_out)
        cat2 = torch.cat([ec22_out, upconv2_out], dim=1) 
        dc21_out = nn.ReLU(inplace=True)(self.dc21(cat2))
        dc22_out = nn.ReLU(inplace=True)(self.dc22(dc21_out))
        
        upconv1_out = self.upconv1(dc22_out)
        cat1 = torch.cat([ec12_out, upconv1_out], dim=1) 
        dc11_out = nn.ReLU(inplace=True)(self.dc11(cat1))
        dc12_out = nn.ReLU(inplace=True)(self.dc12(dc11_out))

        final_out = self.final_conv(dc12_out)

        return final_out
    

class LFWDataset(torch.utils.data.Dataset):
    _DATA = (
        # images
        ("http://vis-www.cs.umass.edu/lfw/lfw-funneled.tgz", None),
        # segmentation masks as ppm
        ("https://vis-www.cs.umass.edu/lfw/part_labels/parts_lfw_funneled_gt_images.tgz",
         "3e7e26e801c3081d651c8c2ef3c45cfc"),
    )
    _SPLIT_NAMES = {
        "train": "https://vis-www.cs.umass.edu/lfw/part_labels/parts_train.txt",
        "validation": "https://vis-www.cs.umass.edu/lfw/part_labels/parts_validation.txt",
        "test": "https://vis-www.cs.umass.edu/lfw/part_labels/parts_test.txt"
    }

    def __init__(self, base_folder, transforms, download=True, split_name: str = 'train'):
        super().__init__()
        self.base_folder = base_folder
        # TODO your code here: if necessary download and extract the data
        # TODO based on the split_name get the paths of the samples and their corresponding ground truth
        anno_path = os.path.join(base_folder, 'parts_lfw_funneled_gt_images')
        img_path = os.path.join(base_folder, 'lfw_funneled')
        if download:
            self.download_resources(base_folder)
        self.transforms = transforms
        self.X = []
        self.Y = [os.path.join(anno_path, img) for img in os.listdir(anno_path) if img[0]!='.' and img.endswith('ppm')]

        for a in self.Y:
            ab = os.path.basename(a)
            self.X.append(os.path.join(img_path, ab[:-9]) + "\\" + ab.replace('.ppm', '.jpg'))
            
    def __len__(self):
        return len(self.X)

    def __getitem__(self, idx):
        # TODO your code here: return the idx^th sample in the dataset: image, segmentation mask
        # TODO your code here: if necessary apply the transforms

        image = Image.open(self.X[idx])
        mask = Image.open(self.Y[idx])
        if(self.transforms is not None):
            image, mask = self.transforms(image, mask)
        return image, mask
    
    def download_resources(self, base_folder):
        if not os.path.exists(base_folder):
            os.makedirs(base_folder)
        self._download_and_extract_archive(url=LFWDataset._DATA[1][0], base_folder=base_folder,
                                           md5=LFWDataset._DATA[1][1])
        self._download_and_extract_archive(url=LFWDataset._DATA[0][0], base_folder=base_folder, md5=None)
        self._download_url(url=LFWDataset._SPLIT_NAMES['train'], base_folder=base_folder, md5=None)
        self._download_url(url=LFWDataset._SPLIT_NAMES['validation'], base_folder=base_folder, md5=None)
        self._download_url(url=LFWDataset._SPLIT_NAMES['test'], base_folder=base_folder, md5=None)

    def _download_and_extract_archive(self, url, base_folder, md5) -> None:
        """
          Downloads an archive file from a given URL, saves it to the specified base folder,
          and then extracts its contents to the base folder.

          Args:
          - url (str): The URL from which the archive file needs to be downloaded.
          - base_folder (str): The path where the downloaded archive file will be saved and extracted.
          - md5 (str): The MD5 checksum of the expected archive file for validation.
          """
        base_folder = os.path.expanduser(base_folder)
        filename = os.path.basename(url)

        self._download_url(url, base_folder, md5)
        archive = os.path.join(base_folder, filename)
        print(f"Extracting {archive} to {base_folder}")
        self._extract_tar_archive(archive, base_folder, True)

    def _retreive(self, url, save_location, chunk_size: int = 1024 * 32) -> None:
        """
            Downloads a file from a given URL and saves it to the specified location.

            Args:
            - url (str): The URL from which the file needs to be downloaded.
            - save_location (str): The path where the downloaded file will be saved.
            - chunk_size (int, optional): The size of each chunk of data to be downloaded. Defaults to 32 KB.
            """
        try:
            response = requests.get(url, stream=True)
            total_size = int(response.headers.get('content-length', 0))

            with open(save_location, 'wb') as file, tqdm(
                    desc=os.path.basename(save_location),
                    total=total_size,
                    unit='B',
                    unit_scale=True,
                    unit_divisor=1024,
            ) as bar:
                for data in response.iter_content(chunk_size=chunk_size):
                    file.write(data)
                    bar.update(len(data))

            print(f"Download successful. File saved to: {save_location}")

        except Exception as e:
            print(f"An error occurred: {str(e)}")

    def _download_url(self, url: str, base_folder: str, md5: str = None) -> None:
        """Downloads the file from the url to the specified folder

        Args:
            url (str): URL to download file from
            base_folder (str): Directory to place downloaded file in
            md5 (str, optional): MD5 checksum of the download. If None, do not check
        """
        base_folder = os.path.expanduser(base_folder)
        filename = os.path.basename(url)
        file_path = os.path.join(base_folder, filename)

        os.makedirs(base_folder, exist_ok=True)

        # check if the file already exists
        if self._check_file(file_path, md5):
            print(f"File {file_path} already exists. Using that version")
            return

        print(f"Downloading {url} to file_path")
        self._retreive(url, file_path)

        # check integrity of downloaded file
        if not self._check_file(file_path, md5):
            raise RuntimeError("File not found or corrupted.")

    def _extract_tar_archive(self, from_path: str, to_path: str = None, remove_finished: bool = False) -> str:
        """Extract a tar archive.

        Args:
            from_path (str): Path to the file to be extracted.
            to_path (str): Path to the directory the file will be extracted to. If omitted, the directory of the file is
                used.
            remove_finished (bool): If True , remove the file after the extraction.
        Returns:
            (str): Path to the directory the file was extracted to.
        """
        if to_path is None:
            to_path = os.path.dirname(from_path)

        with tarfile.open(from_path, "r") as tar:
            tar.extractall(to_path)

        if remove_finished:
            os.remove(from_path)

        return to_path

    def _compute_md5(self, filepath: str, chunk_size: int = 1024 * 1024) -> str:
        with open(filepath, "rb") as f:
            md5 = hashlib.md5()
            while chunk := f.read(chunk_size):
                md5.update(chunk)
        return md5.hexdigest()

    def _check_file(self, filepath: str, md5: str) -> bool:
        if not os.path.isfile(filepath):
            return False
        if md5 is None:
            return True
        return self._compute_md5(filepath) == md5

# define the transformations

transforms_train = v2.Compose([v2.Resize(265),
                       v2.CenterCrop(224),
                       v2.RandomHorizontalFlip(p=0.5),
                       v2.ToTensor()])

transforms_test=v2.Compose([v2.Resize(265), 
                       v2.CenterCrop(224),
                       v2.ToTensor()])

train_dataset = LFWDataset(base_folder='data_lab5', transforms=transforms_train, download=True, split_name='train')
test_dataset = LFWDataset(base_folder='data_lab5', transforms=transforms_test, download=True, split_name='test')

train_dataloader = DataLoader(train_dataset, batch_size=32, shuffle=True, num_workers=0)
validation_dataloader = DataLoader(test_dataset, batch_size=32)

# Define your model
model = UNet()

# Define the loss function and optimizer
loss_function = nn.BCEWithLogitsLoss()
optimizer = torch.optim.Adam(model.parameters(), lr=1e-4)

# Define the number of epochs
num_epochs = 10

for epoch in range(num_epochs):
    model.train()  # Set the model to training mode
    train_loss = 0.0
    train_accuracy = 0.0

    for batch in train_dataloader:
        inputs, targets = batch

        # Zero the gradients
        optimizer.zero_grad()

        # Perform forward pass
        outputs = model(inputs)

        # Compute loss
        loss = loss_function(outputs, targets)
        train_loss += loss.item()

        # Perform backward pass
        loss.backward()

        # Perform optimization
        optimizer.step()

        # Compute the accuracy
        predictions = torch.sigmoid(outputs) > 0.5
        correct = (predictions == targets).float()
        train_accuracy += correct.mean().item()

        # Print intermediate message
        print(f'Step: {epoch+1}/{num_epochs}, Batch Loss: {loss.item():.4f}, Batch Acc: {correct.mean().item():.4f}')

    # Compute average loss and accuracy
    train_loss /= len(train_dataloader)
    train_accuracy /= len(train_dataloader)

    # Validation loop
    model.eval()  # Set the model to evaluation mode
    with torch.no_grad():
        val_loss = 0.0
        val_accuracy = 0.0

        for batch in validation_dataloader:
            inputs, targets = batch

            if len(inputs.shape) == 3:  # Add batch dimension if missing
                inputs = inputs.unsqueeze(0)

            outputs = model(inputs)
            loss = loss_function(outputs, targets)
            val_loss += loss.item()

            # Compute the accuracy
            predictions = torch.sigmoid(outputs) > 0.5
            correct = (predictions == targets).float()
            val_accuracy += correct.mean().item()

    val_loss /= len(validation_dataloader)
    val_accuracy /= len(validation_dataloader)

    # Print statistics
    print(f'Epoch {epoch+1}/{num_epochs}, Train Loss: {train_loss:.4f}, Train Acc: {train_accuracy:.4f}, '
          f'Val Loss: {val_loss:.4f}, Val Acc: {val_accuracy:.4f}')
          
     # Save the model after each epoch
    torch.save(model.state_dict(), f'model_epoch_{epoch+1}.pth')