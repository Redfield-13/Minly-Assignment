const express = require('express')
const path = require('path')
const mysql = require('mysql')
const dotenv = require('dotenv')


dotenv.config({
    path:'./.env',
})


const app = express()

const data_base = mysql.createConnection({
    host: process.env.DATABASE_HOST,
    user: process.env.DATABASE_USER,
    password:process.env.DATABASE_PASSWORD,
    database: process.env.DATABASE
})

const publicDirectory = path.join(__dirname,)
console.log(__dirname);

app.set('view engine', 'hbs')

data_base.connect((err)=>{
    if (err) {
        console.log(err);
    }else{
        console.log("DATABASE conected......");
    }
})

app.get('/', (req,res) =>{
    res.send("<h1> Home Page </h1>")
})


app.listen(3456, ()=>{
    console.log("Server Started On Port : 3456");
})