const mongoose = require('mongoose');
const PedoSchema = new mongoose.Schema({

  uid: {
    type: String,
    default: ''
  },
  
  cb: {
    type: String,
    default: ''
  },
  
  nos: {
    type: String,
    default: ''
  }


});

module.exports = mongoose.model('Pedo', PedoSchema);
