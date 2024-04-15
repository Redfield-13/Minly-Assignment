import React from 'react'
import MenuAppBar from './bar'
import Grid from '@mui/material/Grid';
import UploadButton from './uploadButton'
import '../App.css'
import Post from './post'
import MediaCover from './videoPost';
import { useState, useEffect, useContext, createContext } from 'react'
import UserContex from './Context'
import { useNavigate } from 'react-router-dom';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';

function Upload() {
  const {user , setUser} = useContext(UserContex)
    const navigate = useNavigate()
    let token  = {}

    console.log("yessssssssi :"+JSON.stringify(user));
    const [posts, setPosts] = useState(null)
    let postsUrl = 'http://localhost:3456/getImages?authorID='+user.id
    
    useEffect(()=>{
        fetch(postsUrl).then(res =>{
            return res.json()
        }).then((data) =>{
            setPosts(data)
        })
        token = JSON.parse(localStorage.getItem("userLogged"))
        if (token !== null) {
          setUser(token)
          console.log(setUser);
          setUser(token)
          console.log(user.id);
        }
        
        if (user.id == 0) {
            console.log("heeeeeeeeeey")
            navigate('/login')
        }
    }, [])


  return (
    <Grid container direction="column" spacing={2}>
        <Grid item xs={6} alignItems="center"> <MenuAppBar avatar = {user.avatar}></MenuAppBar> </Grid>
        <Grid item alignContent="center"> <UploadButton ></UploadButton> </Grid>
        <Grid item alignContent="center">
        {posts && (
            posts.length > 0 ? (
                // Check a condition for each post
                posts.map((post) =>
                post.mediaType == "image/jpeg"? (
                    <Post
                    key={post.id}
                    image={post.file_link}
                    author={post.author}
                    likes={post.likes}
                    />
                ) : (
                    <MediaCover
                    key={post.id}
                    media={post.file_link}
                    author={post.author}
                    likes={post.likes}
                    />
                )
                )
            ) : (
              <Box sx={{
                width: '100%',
                maxWidth: 500,
                display: 'grid',
                gridTemplateColumns: 'repeat(auto-fill, minmax(240px, 1fr))',
                gap: 2,
              }}>
                <Card variant="soft" sx={{margin:'auto'}}>
                <CardContent>
                    <Typography level="title-md" textColor="inherit">
                      NO Posts From You!
                    </Typography>
                    <Typography textColor="inherit">Try to post something. Use The Button Above.</Typography>
              </CardContent>  
               </Card>
              </Box>
             
            )
        )}
        </Grid>

    </Grid>
  )
}

export default Upload