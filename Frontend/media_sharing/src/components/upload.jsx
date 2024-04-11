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

function Upload() {
  let user = useContext(UserContex);
  const navigate = useNavigate()

    console.log(user);
    if (user.id == 0) {
      navigate('/login')
  }
    const [posts, setPosts] = useState(null)
    let postsUrl = 'http://localhost:3456/getImages?authorID='+user.id
    
    useEffect(()=>{
        fetch(postsUrl).then(res =>{
            return res.json()
        }).then((data) =>{
            console.log(data.authorPosts);
            setPosts(data.authorPosts)
        })
 
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
                    />
                ) : (
                    <MediaCover
                    key={post.id}
                    media={post.file_link}
                    author={post.author}
                    />
                )
                )
            ) : (
                <p>You Don't have any post yet!! Try to post some</p>
            )
        )}
        </Grid>

    </Grid>
  )
}

export default Upload