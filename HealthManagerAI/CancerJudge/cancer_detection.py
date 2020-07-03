import torch
import torchvision
import torchvision.transforms as transforms
import torch.nn.functional as F
import torch.nn as nn
from torch.utils.data import Dataset, DataLoader

import os
import cv2
from albumentations.pytorch import ToTensor

import warnings
warnings.filterwarnings('ignore')

torch.cuda.empty_cache()
device = 'cuda'

class SimpleCNN(nn.Module):
    def __init__(self):
        super(SimpleCNN, self).__init__()
        self.conv1 = nn.Conv2d(in_channels=3, out_channels=32, kernel_size=3, padding=2)
        self.conv2 = nn.Conv2d(in_channels=32, out_channels=64, kernel_size=3, padding=2)
        self.conv3 = nn.Conv2d(in_channels=64, out_channels=128, kernel_size=3, padding=2)
        self.conv4 = nn.Conv2d(in_channels=128, out_channels=256, kernel_size=3, padding=2)
        self.conv5 = nn.Conv2d(in_channels=256, out_channels=512, kernel_size=3, padding=2)
        self.bn1 = nn.BatchNorm2d(32)
        self.bn2 = nn.BatchNorm2d(64)
        self.bn3 = nn.BatchNorm2d(128)
        self.bn4 = nn.BatchNorm2d(256)
        self.bn5 = nn.BatchNorm2d(512)
        self.pool = nn.MaxPool2d(kernel_size=2, stride=2)
        self.avg = nn.AvgPool2d(8)
        self.fc = nn.Linear(512 * 1 * 1, 2)
    def forward(self, x):
        x = self.pool(F.leaky_relu(self.bn1(self.conv1(x)))) 
        x = self.pool(F.leaky_relu(self.bn2(self.conv2(x))))
        x = self.pool(F.leaky_relu(self.bn3(self.conv3(x))))
        x = self.pool(F.leaky_relu(self.bn4(self.conv4(x))))
        x = self.pool(F.leaky_relu(self.bn5(self.conv5(x))))
        x = self.avg(x)
        x = x.view(-1, 512 * 1 * 1)
        x = self.fc(x)
        return x


class CancerDataset(Dataset):
    def __init__(self, img_path, transform=None):
        self.img_path = img_path
        self.transform = transform

    def __len__(self):
        return 1

    def __getitem__(self, idx):
        if torch.is_tensor(idx):
            idx = idx.tolist()

        image = cv2.imread(self.img_path)

        if self.transform:
            image = self.transform(image)

        sample = {'img': image,'label': torch.Tensor(1)}
        return sample


def predict(img_path):
    model = SimpleCNN().to(device)
    model.load_state_dict(torch.load('CancerJudge/model.ckpt'))

    trans_valid = transforms.Compose([transforms.ToPILImage(),
                                    transforms.Pad(64, padding_mode='reflect'),#对图片进行边界填充
                                    transforms.ToTensor(),                     #将图片转换为tensor
                                    transforms.Normalize(mean=[0.5, 0.5, 0.5],std=[0.5, 0.5, 0.5])])#参数（均值，方差），减均值，除方差



    valset = CancerDataset(img_path, transform= trans_valid)
    val_loader = DataLoader(valset, batch_size=1, drop_last=False, shuffle=False)
    model.eval() #固定model

    # Predict
    with torch.no_grad():
        for batch_index, batch_samples in enumerate(val_loader): #用val_loader
            data = batch_samples['img'].to(device)
            target = batch_samples['label'].to(device)

            output = model(data)
            
            score = F.softmax(output, dim=1)
            pred = output.argmax(dim=1, keepdim=True)
            pred = pred[0, 0].item()
            return pred


# if __name__ == "__main__":
#     predict("./1.jpg")  