import React from 'react'
import MenuAppBar from '../components/bar'
import Grid from '@mui/material/Grid';
import '../App.css'
import Post from '../components/post'
import MediaCover from '../components/videoPost';
import { useState, useEffect, useContext} from 'react'
import UserContex from '../components/Context'
import { useNavigate, useParams } from 'react-router-dom';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';

function Profile(props) {

  // const authorId = props.params.authorId;
  
  const params = useParams()
  console.log('proooooops : ' +  params);

  const {user , setUser} = useContext(UserContex)
    const navigate = useNavigate()
    let token  = {}
    token = JSON.parse(localStorage.getItem("userLogged"))
    console.log("yessssssssi :"+JSON.stringify(user));
    const [posts, setPosts] = useState(null)
    console.log("upppppppp : " , user.id);
    let postsUrl = 'https://k8fm9r7b-3456.uks1.devtunnels.ms/getImages?authorID='+params.authorId
    
    useEffect(()=>{
      token = JSON.parse(localStorage.getItem("userLogged"))
      if (token !== null) {
        setUser(token)
        console.log(setUser);
        setUser(token)
        console.log(user.id);
      }
        fetch(postsUrl).then(res =>{
            return res.json()
        }).then((data) =>{
            setPosts(data)
        })       
        if (user.id == 0) {
            console.log("heeeeeeeeeey")
            navigate('/login')
        }
    }, [])


  return (
    <Grid container direction="column" spacing={2}>
        <Grid item xs={6} alignItems="center"> <MenuAppBar avatar = {user.avatar}></MenuAppBar></Grid>
        <Grid item alignContent="center">
        {posts && (
            posts.length > 0 ? (
                posts.map((post) =>
                post.mediaType == "image/jpeg"? (
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
              <Box sx={{
                width: '100%',
                maxWidth: 500,
                display: 'grid',
                gridTemplateColumns: 'repeat(auto-fill, minmax(340px, 1fr))',
                margin:'auto',
                textAlign:'center'
              }}>
                <Card variant="soft" sx={{margin:'auto'}}>
                <CardContent sx={{margin:'auto'}}>
                    <Typography level="title-md" textColor="inherit">
                      NO Posts From This User!
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

export default Profile