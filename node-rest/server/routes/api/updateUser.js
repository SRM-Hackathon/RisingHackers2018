const User = require('../../models/User');
const bcrypt = require('bcrypt');
module.exports = (app) => {

    app.post('/api/account/edit', (req, res, next) => {

        const { body } = req;

        const {
            uid,
            Name,
            password,
            gender
        } = body;

        var _id = uid;

       

        if (Name.length == 0 && gender.length == 0) {
            
            User.findOneAndUpdate({ _id: _id }, { $set: { password: bcrypt.hashSync(password, bcrypt.genSaltSync(8), null) } }, { new: true }, function (err, doc) {
                if (err) {

                }
                return res.send({
                    success: true,
                    message: "Saved Successfully"
                })

            });
        }

        else if (Name.length == 0 && password.length == 0) {
            User.findOneAndUpdate({ _id: _id }, { $set: { gender: gender } }, { new: true }, function (err, doc) {
                if (err) {

                }
                return res.send({
                    success: true,
                    message: "Saved Successfully"
                })

            });
        }

        else if (gender.length == 0 && password.length == 0) {
            User.findOneAndUpdate({ _id: _id }, { $set: { Name: Name } }, { new: true }, function (err, doc) {
                if (err) {

                }
                return res.send({
                    success: true,
                    message: "Saved Successfully"
                })

            });
        }

        else if (Name.length == 0 && password.length != 0 && gender.length != 0) {
            User.findOneAndUpdate({ _id: _id }, { $set: { gender: gender, password: bcrypt.hashSync(password, bcrypt.genSaltSync(8), null) } }, { new: true }, function (err, doc) {
                if (err) {

                }
                return res.send({
                    success: true,
                    message: "Saved Successfully"
                })

            });
        }

        else if (Name.length != 0 && password.length == 0 && gender.length != 0) {
            User.findOneAndUpdate({ _id: _id }, { $set: { Name: Name, gender: gender } }, { new: true }, function (err, doc) {
                if (err) {

                }
                return res.send({
                    success: true,
                    message: "Saved Successfully"
                })

            });
        }

        else if (Name.length != 0 && password.length != 0 && gender.length == 0) {
            User.findOneAndUpdate({ _id: _id }, { $set: { Name: Name, password: bcrypt.hashSync(password, bcrypt.genSaltSync(8), null) } }, { new: true }, function (err, doc) {
                if (err) {

                }
                return res.send({
                    success: true,
                    message: "Saved SuccessfullyF"
                })

            });
        }

        else {
            User.findOneAndUpdate({ _id: _id }, { $set: { Name: Name, password: bcrypt.hashSync(password, bcrypt.genSaltSync(8), null), gender: gender } }, { new: true }, function (err, doc) {
                if (err) {

                }
                return res.send({
                    success: true,
                    message: "Saved Successfully"
                })

            });

        }










    });

}
