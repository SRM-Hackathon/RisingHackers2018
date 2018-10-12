const mongoose = require('mongoose');
const bmiSchema = new mongoose.Schema({

  uid: {
    type: String,
    default: ''
  },
  
  bmi: {
    type: String,
    default: ''
  },
  
  bmr: {
    type: String,
    default: ''
  }


});

module.exports = mongoose.model('BMI', bmiSchema);

