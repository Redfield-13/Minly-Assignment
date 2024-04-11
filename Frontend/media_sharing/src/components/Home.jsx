import React from 'react'
import AppBar from './bar'
import Post from './post'
import { useState, useEffect, useContext, createContext } from 'react'
import UserContex from './Context'

function Home() {
    let user = useContext(UserContex);
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
        {posts && posts.length > 0 ? (
        posts.map((post) => (
            <Post key ={post.id} image={post.file_link} author={post.author}></Post>
        ))
      ) : (
        <p>No data available</p>
      )}
        
    </div>
  )
}

export default Home