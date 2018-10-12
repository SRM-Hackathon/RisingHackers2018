const User = require('../../models/User');
module.exports = (app) => {

    app.post('/api/account/profile', (req, res, next) => {

        const { body } = req;

        const {
            uid,
        } = body;

        var _id = uid;


        User.find({ _id: _id }, function (err, user) {
            if (err) {

            }
            console.log(user[0]);
            return res.send(user[0]

            )

        });

    });

}
