from django.http import HttpResponse
import CancerJudge.cancer_detection as cj
import CTJudge.CTJudge as ct
from QASystem.prediction import *


def index(request):
    return HttpResponse("Hello, world. You're at the polls index.")

def medical_QA(request):
    if request.method == 'GET':
        department = request.GET.get('department')
        title = request.GET.get('title')
        text = request.GET.get('text')

        # 上面获取了3个参数，调用模型取得结果，返回接口
        # 返回值为   HttpResponse(结果)

        return HttpResponse(predict(department, title, text))


def CT_judge(request):
    if request.method == 'GET':
        picture_location = request.GET.get('picture_location')

        return HttpResponse(ct.predict(picture_location))


def cancer_judge(request):
    if request.method == 'GET':
        picture_location = request.GET.get('picture_location')

        return HttpResponse(cj.predict(picture_location))
