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


router.post('/likes', async (req, res) => {
    try{
        console.log(req.query);
        let sql_q = 'UPDATE UploadedMedia SET likes = ? WHERE id = ?'
        let mediaid = parseInt(req.query.mediaID)
        let likes = parseInt(req.query.currentlikes)
        if (req.query.operation == 'unlike') {
            likes = likes - 1
        }else{likes = likes + 1}
        console.log(likes,mediaid);
        data_base.query(sql_q, [likes , mediaid], (error, results)=>{
            if (error) {
                console.log('Like error : ' + error);
                return error
            }
            else{
                console.log(results);
                return res.status(200).send({code:200, Data: results})
            }
        })
    }catch(error){
        console.log(error);
        return res.status(400).send(error.message)
    }
  });



module.exports = router