import React from 'react'
import AppBar from './bar'
import Post from './post'
import { useState, useEffect, useContext, createContext } from 'react'
import UserContex from './Context'
import MediaCover from './videoPost'
import { useNavigate } from 'react-router-dom';

function Home() {

    const navigate = useNavigate()

    let user = useContext(UserContex);
    if (user.id == 0) {
        navigate('/login')
    }
    console.log(user);
    const [posts, setPosts] = useState(null)
    let postsUrl = 'http://localhost:3456/getImages'
    
    useEffect(()=>{
        fetch(postsUrl).then(res =>{
            return res.json()
        }).then((data) =>{
            console.log(data.Data);
            setPosts(data.Data)
        })
 
    }, [])
  return (
    <div>
        <AppBar avatar = {user.avatar}></AppBar>
        {posts && (
            posts.length > 0 ? (
                // Check a condition for each post
                posts.map((post) =>
                post.mediaType == "image/jpeg" ? (
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
                <p>No data available</p>
            )
        )}
        
    </div>
  )
}

export default Home