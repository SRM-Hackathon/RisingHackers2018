from django.shortcuts import render
from django.http import JsonResponse
from arogya_baba_app.qa import answer_me

# Create your views here.
def index(request):
    file = request.GET['file']
    question = request.GET['question']
    prediction = answer_me(file, question)
    return JsonResponse({'answer': prediction})
