const mysql = require('mysql')
const dotenv = require('dotenv')



dotenv.config({
    path: './.env'
})


const data_base = mysql.createConnection({
    host: 'localhost',
    user: 'minly',
    password:'Mj2002dr%',
    database: 'minly_media',
    port:3306
})


module.exports =  data_base