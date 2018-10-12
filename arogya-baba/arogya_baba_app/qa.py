from allennlp.predictors.predictor import Predictor
import os

def answer_me(topic, question):
    predictor = Predictor.from_path(os.path.join(os.path.dirname(os.path.realpath(__file__)), "bidaf-model-2017.09.15-charpad"))

    path = os.path.join(os.path.dirname(os.path.realpath(__file__)), topic + ".txt")
    file = open(path, "r")
    doc = file.read()

    predict = predictor.predict(
      passage=doc,
      question=question
    )

    return predict['best_span_str']
