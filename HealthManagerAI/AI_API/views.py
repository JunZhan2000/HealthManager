from django.shortcuts import render

from django.http import HttpResponse
import AIFunc.test as test

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

        return HttpResponse("这是新冠肺炎诊断接口\n" + "图片地址： " + picture_location)


def cancer_judge(request):
    if request.method == 'GET':
        picture_location = request.GET.get('picture_location')

        return HttpResponse("这是癌症诊断接口\n" + "图片地址： " + picture_location)