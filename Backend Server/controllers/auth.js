const mysql = require('mysql')
const dotenv = require('dotenv')
const jwt = require('jsonwebtoken')
const bcrypt = require('bcryptjs')


dotenv.config({
    path:'../.env',
})

const data_base = mysql.createConnection({
    host: process.env.DATABASE_HOST,
    user: process.env.DATABASE_USER,
    password:process.env.DATABASE_PASSWORD,
    database: process.env.DATABASE
})


exports.register = (req,res) =>{
    console.log(req.body.name);
    const name = req.body.name
    const email = req.body.email
    const password = req.body.password
    const passwordConfirm = req.body.passwordConfirm

    // const {name,email,password,passwordConfirm} = req.body


    data_base.query('SELECT email FROM users WHERE email = ?', [email],async (error,results) =>{
        if (error) {
            console.log(error);
        }
        if (results.length > 0) {
            return res.send(500, {
                "message": "That email is already taken."
            })
        }
        else if( password != passwordConfirm){
            console.log("passwords no match");
            return res.status(400).send({
                "message": "The passwords do not match."
            })
        }


        let hashed_password = await (await bcrypt.hash(password, 8)).toString()
        console.log(hashed_password);


        data_base.query('INSERT INTO users SET ?',{
            email: email,
            hashed_password: hashed_password,
            name: name

        }, (error,results)=>{
            if (error) {
                console.log(error);
            }else{
                res.status(200).send({
                    message: "USER CREATED"
                })
            }
        })
    })


}


exports.login = async (req,res) =>{
    const email = req.body.email
    const password = req.body.password
    if (!email || !password) {
        return res.json({code:400 ,staus:"error",message:"Email and Password can not be Empty"})
    }
    else{
        data_base.query('SELECT * FROM users WHERE email = ?',[email], async(error,result)=>{
            if (error) {
                throw error
            }
            console.log(result);

            if (!result[0] || !await bcrypt.compare(password, result[0].hashed_password)) {
                return res.json({code:400, status:"error", message:"Incorrect Email or Password"})
            }
            else{
                const token = jwt.sign({id:result[0].id}, process.env.JWT_SECRET, {
                    expiresIn: process.env.JWT_SECRET
                })
                const cookiOption = {
                    expiresIn:new Date(Date.now()+process.env.COOKIE_EXPIRS *24*60*60*1000),
                    httpOnly: true
                }
                res.cookie("userRegistered",token,cookiOption)
                return res.json({code:200, status:"Success", message:"Login Completed"})
            }
        })
    }
    

}