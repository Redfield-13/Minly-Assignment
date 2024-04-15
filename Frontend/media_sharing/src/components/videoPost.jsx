import * as React from 'react';
import Box from '@mui/material/Box';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import HeartBroken from '@mui/icons-material/HeartBroken';
import axios from 'axios';
import { useState, useContext } from 'react';
import UserContex from './Context'

export default function MediaCover(props) {

  let user = useContext(UserContex);
  let [liked,setLiked] = useState(false)
  let [unLiked,setUnLiked] = useState(false)
  const [likes, setLikes] = useState(props.likes);
  const apiUrl = 'https://k8fm9r7b-3456.uks1.devtunnels.ms/likes?liker='+user.id +'&currentlikes='+props.likes+'&mediaID='+props.id+'&operation='
  const handleLike = async () => {
      if (liked) {
          setLikes(likes-1)
          setLiked(false)
      }else{
          try {
              // Make an API request to update the likes count on the server
              const response = await axios.post(apiUrl+'like', {
                headers: {
                  'Content-Type': 'application/json'
                }
              });
              
              // If the API request is successful, update the likes count in the client
              setLikes(likes + 1);
              setLiked(true)
            } catch (error) {
              console.error(error);
            }
      }
    
  };
  const handleunLike = async () => {
      if (unLiked) {
          setLikes(likes + 1)
          setUnLiked(false)
      }
      else{
        try {
          // Make an API request to update the likes count on the server
          const response = await axios.post(apiUrl+'unlike', {
            headers: {
              'Content-Type': 'application/json'
            }
          });
    
    
          // If the API request is successful, update the likes count in the client
          setLikes(likes - 1);
          setUnLiked(true)
        } catch (error) {
          console.error(error);
        }
      }
      
    };

  return (
    <Box
      component="ul"
      sx={{ display: 'flex', gap: 2, flexWrap: 'wrap', p: 0, m: 0 }}
    >
      <Card  component="li" sx={{  boxShadow:10,  margin:'auto', marginTop:5 ,maxWidth: 345, flexGrow: 1 }}>
          <video
            autoPlay
            loop
            muted
            poster={props.media}
            
          >
            <source
              src={props.media}
              type="video/mp4"
            />
          </video>
          <CardContent sx={{marginLeft:4}}>
          <Typography gutterBottom variant="h5" component="div">
             Posted By : {props.author}
          </Typography>
        </CardContent>
        <CardActions sx={{marginLeft:15}}>
        <Button size="small" color="primary">
          <FavoriteBorderIcon onClick={handleLike}></FavoriteBorderIcon>
          <Typography sx={{marginLeft:1, marginRight:1}}>{likes}</Typography>
          <HeartBroken  onClick={handleunLike}></HeartBroken>
        </Button>
      </CardActions>
      </Card>
    </Box>
  );
}