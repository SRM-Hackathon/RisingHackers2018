const Daily = require('../../models/Daily');
const User = require('../../models/User');
module.exports = (app) => {

    app.post('/api/account/daily', (req, res, next) => {

        const { body } = req;
    
        const {
            uid,
            cal,
            day
        } = body;
    
        
    
        Daily.find({ uid: uid }, function (err, user) {
            if (err) {
    
            }
            console.log(user[0]);
            var cal1 = user[0].cal;
            var day1 = user[0].day;
            console.log(cal1);
            console.log(day1);

            if (day1 === -1) {
                console.log('HELLO');
                Daily.findOneAndUpdate({ uid: uid }, { $set: { cal:cal, day:day  } }, { new: true }, function (err, doc) {
                    if (err) {
    
                    }
                    return res.send({
                        success: true,
                        message: "Saved Successfully"
                    })
    
                });
    
            }

            else if (day1 < day) {
                Daily.findOneAndUpdate({ uid: uid }, { $set: { cal:cal, day:day  } }, { new: true }, function (err, doc) {
                    if (err) {
    
                    }
                    return res.send({
                        success: true,
                        message: "Saved Successfully"
                    })
    
                });

            }

            else {
                Daily.findOneAndUpdate({ uid: uid }, { $set: { cal:(cal+cal1), day:day  } }, { new: true }, function (err, doc) {
                    if (err) {
    
                    }
                    return res.send({
                        success: true,
                        message: "Saved Successfully"
                    })
    
                });
            }


            // return res.send(user[0]
    
            // )
    
        });
    
    });
}
