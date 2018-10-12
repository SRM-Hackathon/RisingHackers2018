const mongoose = require('mongoose');
const DailySchema = new mongoose.Schema({

  uid: {
    type: String,
    default: ''
  },
  
  cal: {
    type: Number,
    default: 0
  },
  
  day: {
    type: Number,
    default: -1
  }


});

module.exports = mongoose.model('Daily', DailySchema);
