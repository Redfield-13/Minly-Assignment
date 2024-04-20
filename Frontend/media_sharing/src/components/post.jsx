import * as React from 'react';
import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { Button, CardActionArea, CardActions } from '@mui/material';
import FavoriteBorderIcon from '@mui/icons-material/FavoriteBorder';
import HeartBroken from '@mui/icons-material/HeartBroken';
import DeleteIcon from '@mui/icons-material/Delete';
import axios from 'axios';
import UserContex from './Context'
import { toast } from 'react-toastify';
import { useState , useEffect, useContext } from 'react';
import { Link } from 'react-router-dom';

export default function MultiActionAreaCard(props) {
  console.log(props);
    let user = useContext(UserContex);
    let [liked,setLiked] = useState(false)
    let [unLiked,setUnLiked] = useState(false)
    const [likes, setLikes] = useState(props.likes);
    const apiUrl = 'https://k8fm9r7b-3456.uks1.devtunnels.ms/likes?liker='+user.id +'&currentlikes='+props.likes+'&mediaID='+props.id+'&operation='
    const delUrl = 'https://k8fm9r7b-3456.uks1.devtunnels.ms/delete?imgID='
    const handleLike = async () => {
        if (liked) {
            setLikes(likes-1)
            setLiked(false)
        if (unLiked && !liked) {
          setLikes(likes + 2)
          setLiked(true)
          setUnLiked(false)
        }
        }else{
            try {
                const response = await axios.post(apiUrl+'like', {
                  headers: {
                    'Content-Type': 'application/json'
                  }
                });
                if (!liked) {
                  setLikes(likes + 1);
                  setLiked(true)
                }
                
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
        if (liked && !unLiked) {
          setLikes(likes - 2)
          setLiked(false)
          setUnLiked(true)
        }
        else{
          try {
            const response = await axios.post(apiUrl+'unlike', {
              headers: {
                'Content-Type': 'application/json'
              }
            });
            if (!unLiked) {
              setLikes(likes - 1);
            setUnLiked(true)
            }
            
          } catch (error) {
            console.error(error);
          }
        }
        
      };


      const handleDelete = async ()=>{
        
        try{
          const response = await axios.post(delUrl+props.id,{
            headers: {
              'Content-Type': 'application/json'
            }
          })
          console.log(response);
          toast.info('Media Was Deleted Successfully. Refresh The Page')
        }catch(error){
          console.log(error);
        }
      }


  return (
    <Card sx={{ maxWidth: 345, margin:'auto', marginTop: 5, boxShadow:10}}>
      <CardActionArea>
        <CardMedia
          component='img'
          height="250"
          image={props.image}
          alt="green iguana"
        />
        <Link to={`/profile/${props.authorId}`} style={{ textDecoration: 'none', color: 'inherit', margin:'auto' }}>
          <CardContent sx={{marginLeft:4}}>
            <Typography onClick={() => {console.log(props.authorId);}} gutterBottom variant="h5" component="div">
              Posted By: {props.author}
            </Typography>
          </CardContent>
        </Link>
      </CardActionArea>
      <CardActions sx={{marginLeft:15}}>
        <Button size="small" color="primary">
          <FavoriteBorderIcon  onClick={handleLike}></FavoriteBorderIcon>
          <Typography sx={{marginLeft:1, marginRight:1}}>{likes}</Typography>
          <HeartBroken onClick={handleunLike}></HeartBroken>
        </Button>
        
      </CardActions>
      {props.uploadpage == true && (
              <Button sx={{marginLeft:17.2}} size="small" color="error">
                <DeleteIcon onClick={handleDelete}></DeleteIcon>
              </Button>
            )}
    </Card>
  );
}