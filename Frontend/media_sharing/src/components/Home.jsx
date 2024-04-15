import React from 'react'
import AppBar from './bar'
import Post from './post'
import { useState, useEffect, useContext, createContext } from 'react'
import UserContex from './Context'
import MediaCover from './videoPost'
import { useNavigate } from 'react-router-dom';

function Home() {

    const {user , setUser} = useContext(UserContex)
    const navigate = useNavigate()
    let token  = {}
    console.log("yessssssssi :"+JSON.stringify(user));
    const [posts, setPosts] = useState(null)
    let postsUrl = 'https://k8fm9r7b-3456.uks1.devtunnels.ms/getImages'

    useEffect(()=>{
        fetch(postsUrl).then(res =>{
            return res.json()
        }).then((data) =>{
            setPosts(data)
        })
        token = JSON.parse(localStorage.getItem("userLogged"))
        console.log("toooooooooook",token);
        if (token !== null) {
            setUser(token)
            console.log(setUser);
            setUser(token)
            console.log(user.id);
        }
        if (token == null) {
            navigate('/login')
        }
        
        if (user.id == 0) {
            console.log("heeeeeeeeeey")
            navigate('/login')
        }
    }, [])
  
    
    
  return (
    <div>
        <AppBar avatar = {user.avatar}></AppBar>
        {posts && (
            posts.length > 0 ? (
                // Check a condition for each post
                posts.slice().reverse().map((post) =>
                post.mediaType == "image/jpeg" ? (
                    <Post
                    key={post.id}
                    image={post.file_link}
                    author={post.author}
                    likes={post.likes}
                    id={post.id}
                    />
                ) : (
                    <MediaCover
                    key={post.id}
                    media={post.file_link}
                    author={post.author}
                    likes={post.likes}
                    id={post.id}
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