from keras.models import load_model
from keras.preprocessing import image
from keras.applications.vgg19 import VGG19, preprocess_input
from keras.models import Sequential, model_from_json
from keras.layers import Dense, Dropout, Activation
import requests
import numpy as np
import os
from urllib.request import urlretrieve
import random
import keras.backend as K

def get_file(path, file):
    pic = urlretrieve(path, file)
    return pic

def predict(img_path):
    K.clear_session()

    model_struct_file = os.path.join(os.path.dirname(os.path.realpath(__file__)), 'model_struct.json')
    f = open(model_struct_file, 'r')
    loaded_model_json = f.read()
    f.close()
    model = model_from_json(loaded_model_json)
    model.load_weights(os.path.join(os.path.dirname(os.path.realpath(__file__)), 'weights.h5'))
    vgg19_model = VGG19(weights='imagenet', include_top=False)
    n = random.randint(10000, 20000)
    file = str(n) + ".jpeg"
    filename = os.path.join(os.path.dirname(os.path.realpath(__file__)), file)
    pic = get_file(img_path, filename)
    img = image.load_img(filename, target_size=(224, 224))
    img_data = image.img_to_array(img)
    img_data = np.expand_dims(img_data, axis=0)
    img_data = preprocess_input(img_data)
    vgg19_feature = vgg19_model.predict(img_data)
    vgg19_feature = vgg19_feature.flatten()
    vgg19_feature = vgg19_feature.reshape(1, 25088)
    prediction = model.predict(vgg19_feature)
    os.remove(filename)
    return str(np.argmax(prediction))
