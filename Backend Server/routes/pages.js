const express = require('express')
const data_base = require('../configs/databaseConfig')


const router = express.Router()

router.get('/', (req,res)=>{
    console.log("Welcome to my API");
    res.send({message:"Welcome to my API"})
})


router.get('/getImages', (req,res) =>{
    console.log("get the images");
    let sql_q = 'SELECT * FROM UploadedMedia'
    let authorID = req.query.authorID
    data_base.query(sql_q, (error,results) =>{
        if (error) {
            console.log('UploadMedia table error : '+error);
            return error;
        }else{
            let authorPosts;
            console.log('Data : ' + results);
            if (authorID) {
                authorPosts = results.filter((post) => post.author_id === parseInt(authorID))
            }
            
            return res.status(200).send({code: 200, Data: results, authorPosts: authorPosts})
        }
        
    })
})



module.exports = router