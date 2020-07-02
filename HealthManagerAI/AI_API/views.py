from django.http import HttpResponse
import CancerJudge.test as test
import CancerJudge.cancer_detection as cj
import CTJudge.CTJudge as ct


def index(request):
    return HttpResponse("Hello, world. You're at the polls index.")

def medical_QA(request):
    if request.method == 'GET':
        department = request.GET.get('department')
        title = request.GET.get('title')
        text = request.GET.get('text')

        return HttpResponse(test.medical_QA(title=title, text=text, department=department))


def CT_judge(request):
    if request.method == 'GET':
        picture_location = request.GET.get('picture_location')
        print("图片地址: " + picture_location)
        return HttpResponse("img's path:" + picture_location)
        # return HttpResponse(ct.predict(picture_location))


def cancer_judge(request):
    if request.method == 'GET':
        picture_location = request.GET.get('picture_location')
        print("图片地址: " + picture_location)
        return HttpResponse("img's path:" + picture_location)
        # return HttpResponse(cj.predict(picture_location))
