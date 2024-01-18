import torch

class UpsampleBlock(torch.nn.Module):

    def __init__(
        self, 
        in_channels: int,
        filters: int, 
        size: int = 1,
        stride: int = 2,
        padding: int = 1
    ):
        super(UpsampleBlock, self).__init__()

        self.upsample = torch.nn.ConvTranspose2d(in_channels = in_channels, out_channels = filters, kernel_size = size, stride = stride, padding = padding)
        self.bn = torch.nn.BatchNorm2d(num_features = filters)
        self.relu = torch.nn.ReLU()


    def forward(
        self, 
        x: torch.Tensor
    ):
        x = self.upsample(x)
        x = self.bn(x)
        x = self.relu(x)

        return x