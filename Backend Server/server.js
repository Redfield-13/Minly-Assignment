const express = require('express')
const path = require('path')
const mysql = require('mysql')
const dotenv = require('dotenv')
const cors = require('cors')



dotenv.config({
    path:'./.env',
})


const app = express()

app.use(cors())


const data_base = mysql.createConnection({
    host: process.env.DATABASE_HOST,
    user: process.env.DATABASE_USER,
    password:process.env.DATABASE_PASSWORD,
    database: process.env.DATABASE
})

const publicDirectory = path.join(__dirname,)
app.use(express.static(publicDirectory))

app.use(express.urlencoded({extended: false}))
app.use(express.json())


app.set('view engine', 'hbs')

data_base.connect((err)=>{
    if (err) {
        console.log(err);
    }else{
        console.log("DATABASE conected......");
    }
})
// Define Routes


app.use('/',require('./routes/pages'))

app.use('/auth', require('./routes/auth'))



app.listen(3456, ()=>{
    console.log("Server Started On Port : 3456");
})