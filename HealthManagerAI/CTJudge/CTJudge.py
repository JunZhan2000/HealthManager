import torch
import torchvision
import torchvision.transforms as transforms
import torch.nn.functional as F
import torch.nn as nn
from torch.utils.data import Dataset, DataLoader

import os
from PIL import Image
from albumentations.pytorch import ToTensor


class CovidCTDataset(Dataset):
    def __init__(self, img_path,transform=None):
        self.img_path = img_path
        self.transform = transform

    def __len__(self):
        return 1

    def __getitem__(self, idx):
        if torch.is_tensor(idx):
            idx = idx.tolist()

        image = Image.open(self.img_path).convert('RGB')

        if self.transform:
            image = self.transform(image)

        sample = {'img': image,'label': torch.Tensor(1)}
        return sample

def predict(img_path):
    torch.cuda.empty_cache()
    device = 'cuda'

    ### load Dense169
    import torchvision.models as models
    model = models.densenet169(pretrained=True).cuda()
    model.load_state_dict(torch.load("CTJudge/DenseNet169.pth"))

    import warnings
    warnings.filterwarnings('ignore')

    normalize = transforms.Normalize(mean=[0.485, 0.456, 0.406], std=[0.229, 0.224, 0.225])
    val_transformer = transforms.Compose([
        transforms.Resize(224),
        transforms.CenterCrop(224),
        transforms.ToTensor(),
        normalize
    ])

    valset = CovidCTDataset(img_path, transform= val_transformer)
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
            pred = pred.item()
            print(pred)
            return pred