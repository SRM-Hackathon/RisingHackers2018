const BMI = require('../../models/BMI');
module.exports = (app) => {

  app.post('/api/account/bmr', (req, res, next) => {

    const { body } = req;

    const {
      uid,
      bmi,
      bmr
    } = body;



    BMI.find({ uid: uid }, function (err, user) {

      if (err) {
        res.send(err);
      }
      console.log(user);
      if (user && user.length) {

        BMI.findOneAndUpdate({ uid: uid }, { $set: { bmr: bmr } }, { new: true }, function (err, doc) {
          if (err) {

          }
          return res.send({
            success: true,
            message: "Saved Successfully"
          })

        });

      }
      else {

        const newBmi = new BMI();
        newBmi.uid = uid;
        newBmi.bmi = bmi;
        newBmi.bmr = bmr;
        newBmi.save((err, user) => {
          if (err) {
            return res.send({
              success: false,
              message: 'Error: Server error'
            });
          }
          return res.send({
            success: true,
            message: 'Saved Successfully'
          });
        });

      }
    })






  });


  app.post('/api/account/fetchbmr', (req, res, next) => {

    const { body } = req;

    const {
        uid,
    } = body;

    

    BMI.find({ uid: uid }, function (err, user) {
        if (err) {

        }
        console.log(user[0]);
        return res.send(user[0]

        )

    });

});

}
