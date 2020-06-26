from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('medical_QA', views.medical_QA, name='medical_Q'),
    path('CT_judge', views.CT_judge, name='CT_judge'),
    path('cancer_judge', views.cancer_judge, name='cancer_judge')
]