import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import HeartBroken from '@mui/icons-material/HeartBroken';
import axios from 'axios';
import { useState , useEffect } from 'react';

export default function MultiActionAreaCard(props) {
    let liked = false
    let unliked = false
    const [likes, setLikes] = useState(props.likes);
    const apiUrl = 'http://localhost:3456/likes?currentlikes='+props.likes+'&mediaID='+props.id+'&operation='
    const handleLike = async () => {
        if (liked) {
            setLikes(likes-1)
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
                liked = true
              } catch (error) {
                console.error(error);
              }
        }
      
    };
    const handleunLike = async () => {
        if (unliked) {
            setLikes(likes + 1)
        }
        try {
          // Make an API request to update the likes count on the server
          const response = await axios.post(apiUrl+'unlike', {
            headers: {
              'Content-Type': 'application/json'
            }
          });
    
    
          // If the API request is successful, update the likes count in the client
          setLikes(likes - 1);
        } catch (error) {
          console.error(error);
        }
      };


  return (
    <Card sx={{ maxWidth: 345, margin:'auto', marginTop: 5, boxShadow:10}}>
      <CardActionArea>
        <CardMedia
          component='img'
          height="250"
          image={props.image}
          alt="green iguana"
        />
        <CardContent sx={{marginLeft:4}}>
          <Typography gutterBottom variant="h5" component="div">
             Posted By : {props.author}
          </Typography>
        </CardContent>
      </CardActionArea>
      <CardActions sx={{marginLeft:15}}>
        <Button size="small" color="primary">
          <FavoriteBorderIcon  onClick={handleLike}></FavoriteBorderIcon>
          <Typography sx={{marginLeft:1, marginRight:1}}>{likes}</Typography>
          <HeartBroken onClick={handleunLike}></HeartBroken>
        </Button>
      </CardActions>
    </Card>
  );
}