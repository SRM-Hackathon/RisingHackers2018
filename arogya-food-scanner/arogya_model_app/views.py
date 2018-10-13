from django.shortcuts import render
from django.http import JsonResponse
from arogya_model_app.predict_image import predict
from arogya_model_app.minify import minify_url
import logging

def get_current_path(request):
    return {
       'current_path': request.get_full_path()
     }

# Create your views here.
def index(requests):
    img = get_current_path(requests)['current_path']
    img = img.split("img=")[1]
    img = minify_url(img)
    prediction = predict(img)
    return JsonResponse({'prediction': prediction})
