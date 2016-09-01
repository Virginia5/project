# -*- coding: utf-8 -*-

#from django.http import HttpResponse
from django.shortcuts import render
import os

def index(request):
    return render(request,'index.html')


def hello(request):
    context = {}
    base = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))

    context['hello'] = base
    return render(request, 'hello.html', context)
