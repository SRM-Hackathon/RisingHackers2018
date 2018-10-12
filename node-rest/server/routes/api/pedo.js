
const Pedo = require('../../models/Pedo');
module.exports = (app) => {

    app.post('/api/account/pedo', (req, res, next) => {

        const { body } = req;

        const {
            uid,
            cb,
            nos
        } = body;



        Pedo.find({ uid: uid }, function (err, user) {

            if (err) {
                res.send(err);
            }
            console.log(user);
            if (user && user.length) {
                if(cb.length == 0) {
                    Pedo.findOneAndUpdate({ uid: uid }, { $set: {nos: nos } }, { new: true }, function (err, doc) {
                        if (err) {
    
                        }
                        return res.send({
                            success: true,
                            message: "FUCK OFF"
                        })
    
                    });
                }
                else if(nos.length == 0) {
                    Pedo.findOneAndUpdate({ uid: uid }, { $set: { cb: cb} }, { new: true }, function (err, doc) {
                        if (err) {
    
                        }
                        return res.send({
                            success: true,
                            message: "FUCK OFF"
                        })
    
                    }); 
                }
                else {
                    Pedo.findOneAndUpdate({ uid: uid }, { $set: { cb: cb, nos: nos } }, { new: true }, function (err, doc) {
                        if (err) {
    
                        }
                        return res.send({
                            success: true,
                            message: "FUCK OFF"
                        })
    
                    });
                }
            

            }
            else {

                const newPedo = new Pedo();
                newPedo.uid = uid;
                newPedo.cb = cb;
                newPedo.nos = nos;
                newPedo.save((err, user) => {
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


    app.post('/api/account/fetchnos', (req, res, next) => {

        const { body } = req;
    
        const {
            uid,
        } = body;
    
        
    
        Pedo.find({ uid: uid }, function (err, user) {
            if (err) {
    
            }
            console.log(user[0]);
            return res.send(user[0]
    
            )
    
        });
    
    });


}
